package com.jayys.stashmap.core.data.settings

import com.jayys.stashmap.core.data.sharedpreferences.FakePreferenceStorage
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys
import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Locale

/**
 * [SettingsRepositoryImpl] 단위 테스트.
 *
 * 이 저장소는 "저장(PreferenceStorage) + 상태(StateFlow)"를 **한 번의 호출로 둘 다** 수행하는
 * 단일 진실 공급원이다. 둘 중 하나라도 빠지면 다음 회귀가 재발한다.
 *
 * - 저장만 되고 상태가 안 바뀌면 → 구독 중인 `BaseActivity`가 recreate 되지 않아 화면이 안 바뀐다
 * - 상태만 바뀌고 저장이 안 되면 → 앱을 재시작하거나 `attachBaseContext`가 옛 값을 읽는다
 *
 * 따라서 모든 쓰기 테스트는 **저장과 상태를 함께** 단언한다.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryImplTest {

    private lateinit var originalLocale: Locale

    @Before
    fun setUp() {
        // systemDefault()가 Locale.getDefault()에 의존하므로 테스트를 결정적으로 만든다.
        originalLocale = Locale.getDefault()
        Locale.setDefault(Locale.KOREAN)
    }

    @After
    fun tearDown() {
        Locale.setDefault(originalLocale)
    }

    // ---------------------------------------------------------------------
    // 초기 상태 구성 (생성 시점에 저장된 값을 읽는가)
    // ---------------------------------------------------------------------

    @Test
    fun `저장된 언어 코드가 있으면 초기 language는 저장된 언어다`() {
        val storage = FakePreferenceStorage(
            mapOf(SharedPreferenceKeys.KEY_LANGUAGE to StashMapLanguage.ENGLISH.code)
        )

        val repository = SettingsRepositoryImpl(storage)

        assertEquals(StashMapLanguage.ENGLISH, repository.language.value)
    }

    @Test
    fun `저장된 언어 코드가 없으면 초기 language는 시스템 기본 언어다`() {
        val repository = SettingsRepositoryImpl(FakePreferenceStorage())

        // setUp에서 시스템 로케일을 ko로 고정했다.
        assertEquals(StashMapLanguage.KOREAN, repository.language.value)
    }

    @Test
    fun `저장된 언어 코드를 해석할 수 없으면 초기 language는 시스템 기본 언어로 폴백한다`() {
        val storage = FakePreferenceStorage(
            mapOf(SharedPreferenceKeys.KEY_LANGUAGE to "알수없는코드")
        )

        val repository = SettingsRepositoryImpl(storage)

        assertEquals(StashMapLanguage.KOREAN, repository.language.value)
    }

    @Test
    fun `저장된 다크모드 값이 있으면 초기 darkMode는 저장된 값이다`() {
        val storage = FakePreferenceStorage(
            mapOf(SharedPreferenceKeys.KEY_THEME_MODE to true)
        )

        val repository = SettingsRepositoryImpl(storage)

        assertTrue(repository.darkMode.value)
    }

    @Test
    fun `저장된 다크모드 값이 없으면 초기 darkMode는 false다`() {
        val repository = SettingsRepositoryImpl(FakePreferenceStorage())

        assertFalse(repository.darkMode.value)
    }

    // ---------------------------------------------------------------------
    // setLanguage — 이번 회귀의 진원지
    // ---------------------------------------------------------------------

    @Test
    fun `setLanguage가 완료되면 language StateFlow가 새 언어로 갱신된다`() = runTest {
        val repository = SettingsRepositoryImpl(
            FakePreferenceStorage(mapOf(SharedPreferenceKeys.KEY_LANGUAGE to StashMapLanguage.KOREAN.code))
        )

        // setLanguage 내부의 withContext(Dispatchers.IO)가 끝날 때까지 실제로 대기한다.
        repository.setLanguage(StashMapLanguage.ENGLISH)

        assertEquals(StashMapLanguage.ENGLISH, repository.language.value)
    }

    @Test
    fun `setLanguage가 완료되면 언어 코드가 PreferenceStorage에 저장된다`() = runTest {
        val storage = FakePreferenceStorage()
        val repository = SettingsRepositoryImpl(storage)

        repository.setLanguage(StashMapLanguage.ENGLISH)

        assertEquals(StashMapLanguage.ENGLISH.code, storage.getString(SharedPreferenceKeys.KEY_LANGUAGE))
    }

    @Test
    fun `setLanguage는 저장과 상태 갱신을 모두 수행한다`() = runTest {
        val storage = FakePreferenceStorage(
            mapOf(SharedPreferenceKeys.KEY_LANGUAGE to StashMapLanguage.KOREAN.code)
        )
        val repository = SettingsRepositoryImpl(storage)

        repository.setLanguage(StashMapLanguage.ENGLISH)

        // 둘 중 하나만 통과하면 언어 전환 회귀가 재발하므로 반드시 함께 단언한다.
        assertEquals(
            "영속화가 누락되면 attachBaseContext가 옛 언어를 읽는다",
            StashMapLanguage.ENGLISH.code,
            storage.getString(SharedPreferenceKeys.KEY_LANGUAGE)
        )
        assertEquals(
            "상태 갱신이 누락되면 구독 중인 Activity가 recreate 되지 않는다",
            StashMapLanguage.ENGLISH,
            repository.language.value
        )
    }

    @Test
    fun `setLanguage로 저장한 언어는 새로 생성된 저장소의 초기값으로 읽힌다`() = runTest {
        val storage = FakePreferenceStorage()
        SettingsRepositoryImpl(storage).setLanguage(StashMapLanguage.ENGLISH)

        // Activity recreate / 앱 재시작 시나리오: 저장소를 새로 만들어도 값이 살아 있어야 한다.
        val recreated = SettingsRepositoryImpl(storage)

        assertEquals(StashMapLanguage.ENGLISH, recreated.language.value)
    }

    @Test
    fun `language 구독자는 setLanguage 이후 새 언어를 통지받는다`() = runTest {
        val repository = SettingsRepositoryImpl(
            FakePreferenceStorage(mapOf(SharedPreferenceKeys.KEY_LANGUAGE to StashMapLanguage.KOREAN.code))
        )
        val emitted = mutableListOf<StashMapLanguage>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.language.collect { emitted += it }
        }

        repository.setLanguage(StashMapLanguage.ENGLISH)
        advanceUntilIdle()

        // BaseActivity는 이 통지를 받아 recreate()를 호출한다.
        assertEquals(listOf(StashMapLanguage.KOREAN, StashMapLanguage.ENGLISH), emitted)
    }

    @Test
    fun `같은 언어를 다시 저장해도 중복 통지하지 않는다`() = runTest {
        val repository = SettingsRepositoryImpl(
            FakePreferenceStorage(mapOf(SharedPreferenceKeys.KEY_LANGUAGE to StashMapLanguage.KOREAN.code))
        )
        val emitted = mutableListOf<StashMapLanguage>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.language.collect { emitted += it }
        }

        repository.setLanguage(StashMapLanguage.KOREAN)
        advanceUntilIdle()

        // 같은 값이면 StateFlow가 통지하지 않으므로 불필요한 recreate 루프가 생기지 않는다.
        assertEquals(listOf(StashMapLanguage.KOREAN), emitted)
    }

    // ---------------------------------------------------------------------
    // setDarkMode
    // ---------------------------------------------------------------------

    @Test
    fun `setDarkMode는 저장과 상태 갱신을 모두 수행한다`() = runTest {
        val storage = FakePreferenceStorage()
        val repository = SettingsRepositoryImpl(storage)

        repository.setDarkMode(true)

        assertEquals(true, storage.getBoolean(SharedPreferenceKeys.KEY_THEME_MODE))
        assertTrue(repository.darkMode.value)
    }

    @Test
    fun `setDarkMode로 false를 저장하면 저장과 상태가 모두 false가 된다`() = runTest {
        val storage = FakePreferenceStorage(
            mapOf(SharedPreferenceKeys.KEY_THEME_MODE to true)
        )
        val repository = SettingsRepositoryImpl(storage)

        repository.setDarkMode(false)

        assertEquals(false, storage.getBoolean(SharedPreferenceKeys.KEY_THEME_MODE))
        assertFalse(repository.darkMode.value)
    }

    @Test
    fun `setDarkMode로 저장한 값은 새로 생성된 저장소의 초기값으로 읽힌다`() = runTest {
        val storage = FakePreferenceStorage()
        SettingsRepositoryImpl(storage).setDarkMode(true)

        val recreated = SettingsRepositoryImpl(storage)

        assertTrue(recreated.darkMode.value)
    }
}
