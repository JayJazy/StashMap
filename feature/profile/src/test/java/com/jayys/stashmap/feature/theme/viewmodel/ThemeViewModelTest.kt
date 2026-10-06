package com.jayys.stashmap.feature.theme.viewmodel

import com.jayys.stashmap.core.testing.MainDispatcherRule
import com.jayys.stashmap.feature.testing.FakeSettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * [ThemeViewModel] 단위 테스트
 *
 * 검증 포인트:
 * 1. `selectTheme()` 는 `launch { }` 기반 → 코루틴 완료 뒤에야 상태 반영
 *    (언어 전환 회귀와 같은 모양 — 저장 완료 전에 읽으면 옛 값)
 * 2. 저장소 위임이 정확히 1회 → 중복 호출은 불필요한 재구성 유발
 * 3. `isDarkMode` 는 저장소 StateFlow 를 그대로 노출 → 별도 사본 없음
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ThemeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var settingsRepository: FakeSettingsRepository
    private lateinit var viewModel: ThemeViewModel

    @Before
    fun setUp() {
        settingsRepository = FakeSettingsRepository(initialDarkMode = false)
        viewModel = ThemeViewModel(settingsRepository)
    }

    // ---------------------------------------------------------------------
    // selectTheme — 비동기 완료 시점 검증
    // ---------------------------------------------------------------------

    @Test
    fun `selectTheme는 코루틴이 완료된 뒤에 isDarkMode에 반영된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            assertFalse(viewModel.isDarkMode.value)

            viewModel.selectTheme(true)

            // launch { } 미실행 시점 — 이때 읽으면 옛 값
            assertFalse(viewModel.isDarkMode.value)

            advanceUntilIdle()

            assertTrue(viewModel.isDarkMode.value)
        }

    @Test
    fun `selectTheme는 선택한 값을 저장소에 위임한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            assertEquals(0, settingsRepository.setDarkModeCallCount)
            assertNull(settingsRepository.lastSetDarkMode)

            viewModel.selectTheme(true)
            advanceUntilIdle()

            // 정확히 1회 — 중복 위임은 불필요한 테마 재구성으로 이어짐
            assertEquals(1, settingsRepository.setDarkModeCallCount)
            assertEquals(true, settingsRepository.lastSetDarkMode)
        }

    @Test
    fun `selectTheme는 다크에서 라이트로도 전환된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            settingsRepository = FakeSettingsRepository(initialDarkMode = true)
            viewModel = ThemeViewModel(settingsRepository)
            assertTrue(viewModel.isDarkMode.value)

            viewModel.selectTheme(false)
            advanceUntilIdle()

            assertFalse(viewModel.isDarkMode.value)
            assertEquals(false, settingsRepository.lastSetDarkMode)
        }

    // ---------------------------------------------------------------------
    // isDarkMode — 저장소 상태 노출 특성
    // ---------------------------------------------------------------------

    @Test
    fun `isDarkMode 초기값은 저장소의 초기 상태를 따른다`() {
        val darkRepository = FakeSettingsRepository(initialDarkMode = true)

        val darkViewModel = ThemeViewModel(darkRepository)

        assertTrue(darkViewModel.isDarkMode.value)
    }

    @Test
    fun `isDarkMode는 저장소를 직접 바꿔도 반영된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // ViewModel 이 별도 사본 없이 저장소 StateFlow 를 그대로 참조
            settingsRepository.setDarkMode(true)

            assertTrue(viewModel.isDarkMode.value)
        }
}
