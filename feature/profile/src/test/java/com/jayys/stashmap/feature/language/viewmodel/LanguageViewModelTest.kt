package com.jayys.stashmap.feature.language.viewmodel

import com.jayys.stashmap.core.model.StashMapLanguage
import com.jayys.stashmap.feature.testing.FakeSettingsRepository
import com.jayys.stashmap.feature.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * [LanguageViewModel] 단위 테스트.
 *
 * 검증 포인트 두 가지:
 * 1. `selectLanguage()`는 `launch { }` 기반이므로 **코루틴이 완료된 뒤에야** 상태가 반영된다.
 *    (완료 전에 읽는 것이 이번 언어 전환 회귀의 본질이었다)
 * 2. `uiState`는 `SharingStarted.WhileSubscribed`라서 **구독자가 있어야** 갱신된다.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LanguageViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var settingsRepository: FakeSettingsRepository
    private lateinit var viewModel: LanguageViewModel

    @Before
    fun setUp() {
        settingsRepository = FakeSettingsRepository(initialLanguage = StashMapLanguage.KOREAN)
        viewModel = LanguageViewModel(settingsRepository)
    }

    /**
     * `uiState`는 `WhileSubscribed`이므로 구독자가 없으면 upstream이 돌지 않는다.
     * 테스트 종료 시 자동 취소되는 [TestScope.backgroundScope]에서 수집을 시작해 둔다.
     */
    private fun TestScope.startCollectingUiState() {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect { }
        }
    }

    // ---------------------------------------------------------------------
    // selectLanguage — 비동기 완료 시점 검증
    // ---------------------------------------------------------------------

    @Test
    fun `selectLanguage는 코루틴이 완료된 뒤에 selectedLanguage에 반영된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            assertEquals(StashMapLanguage.KOREAN, viewModel.selectedLanguage.value)

            viewModel.selectLanguage(StashMapLanguage.ENGLISH)

            // launch { }가 아직 실행되지 않은 시점 — 이때 읽으면 옛 값이 보인다.
            // (저장 직후 동기로 recreate()를 호출해 언어가 안 바뀌던 회귀의 원인)
            assertEquals(StashMapLanguage.KOREAN, viewModel.selectedLanguage.value)

            advanceUntilIdle()

            assertEquals(StashMapLanguage.ENGLISH, viewModel.selectedLanguage.value)
        }

    @Test
    fun `selectLanguage는 선택한 언어를 저장소에 위임한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            viewModel.selectLanguage(StashMapLanguage.ENGLISH)
            advanceUntilIdle()

            assertEquals(1, settingsRepository.setLanguageCallCount)
            assertEquals(StashMapLanguage.ENGLISH, settingsRepository.lastSetLanguage)
        }

    @Test
    fun `selectLanguage 결과는 구독 중인 uiState의 selectedLanguage에도 반영된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            advanceUntilIdle()

            viewModel.selectLanguage(StashMapLanguage.ENGLISH)
            advanceUntilIdle()

            assertEquals(StashMapLanguage.ENGLISH, viewModel.uiState.value.selectedLanguage)
        }

    @Test
    fun `selectedLanguage는 저장소의 언어 상태를 그대로 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // ViewModel이 별도 사본을 들지 않고 저장소(단일 진실 공급원)를 그대로 본다.
            assertEquals(settingsRepository.language.value, viewModel.selectedLanguage.value)

            settingsRepository.setLanguage(StashMapLanguage.ENGLISH)

            assertEquals(StashMapLanguage.ENGLISH, viewModel.selectedLanguage.value)
        }

    // ---------------------------------------------------------------------
    // uiState 초기값 / 구독 특성
    // ---------------------------------------------------------------------

    @Test
    fun `uiState 초기값은 현재 선택된 언어와 전체 언어 목록이다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val initial = viewModel.uiState.value

            assertEquals("", initial.searchQuery)
            assertEquals(StashMapLanguage.KOREAN, initial.selectedLanguage)
            assertEquals(StashMapLanguage.entries, initial.availableLanguages)
        }

    @Test
    fun `구독자가 없으면 uiState는 갱신되지 않는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // WhileSubscribed 특성상 수집을 시작하지 않으면 초기값에 머무른다.
            viewModel.onSearchQueryChange("한국")
            advanceUntilIdle()

            assertEquals("", viewModel.uiState.value.searchQuery)
            assertEquals(StashMapLanguage.entries, viewModel.uiState.value.availableLanguages)
        }

    // ---------------------------------------------------------------------
    // onSearchQueryChange — 필터링
    // ---------------------------------------------------------------------

    @Test
    fun `검색어가 표시 이름과 일치하면 해당 언어만 남는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            advanceUntilIdle()

            viewModel.onSearchQueryChange("한국")
            advanceUntilIdle()

            assertEquals("한국", viewModel.uiState.value.searchQuery)
            assertEquals(listOf(StashMapLanguage.KOREAN), viewModel.uiState.value.availableLanguages)
        }

    @Test
    fun `검색어가 언어 코드와 일치하면 해당 언어만 남는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            advanceUntilIdle()

            viewModel.onSearchQueryChange(StashMapLanguage.ENGLISH.code)
            advanceUntilIdle()

            assertEquals(listOf(StashMapLanguage.ENGLISH), viewModel.uiState.value.availableLanguages)
        }

    @Test
    fun `검색어는 대소문자를 구분하지 않는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            advanceUntilIdle()

            viewModel.onSearchQueryChange("ENGLISH")
            advanceUntilIdle()

            assertEquals(listOf(StashMapLanguage.ENGLISH), viewModel.uiState.value.availableLanguages)
        }

    @Test
    fun `검색어가 비어 있으면 전체 언어 목록을 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            advanceUntilIdle()

            viewModel.onSearchQueryChange("한국")
            advanceUntilIdle()
            viewModel.onSearchQueryChange("")
            advanceUntilIdle()

            assertEquals(StashMapLanguage.entries, viewModel.uiState.value.availableLanguages)
        }

    @Test
    fun `일치하는 언어가 없으면 availableLanguages는 비어 있다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            advanceUntilIdle()

            viewModel.onSearchQueryChange("존재하지않는언어")
            advanceUntilIdle()

            assertTrue(viewModel.uiState.value.availableLanguages.isEmpty())
        }

    @Test
    fun `검색 중에도 선택된 언어는 유지된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            advanceUntilIdle()

            viewModel.selectLanguage(StashMapLanguage.ENGLISH)
            advanceUntilIdle()
            viewModel.onSearchQueryChange("한국")
            advanceUntilIdle()

            assertEquals(listOf(StashMapLanguage.KOREAN), viewModel.uiState.value.availableLanguages)
            assertEquals(StashMapLanguage.ENGLISH, viewModel.uiState.value.selectedLanguage)
        }
}
