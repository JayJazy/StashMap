package com.jayys.stashmap.feature.home.viewmodel

import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.feature.home.model.HomeSampleData
import com.jayys.stashmap.feature.home.model.HomeUiState
import com.jayys.stashmap.feature.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * [HomeViewModel] 단위 테스트
 *
 * 저장소가 없어 주입할 더블도 없고 입력이 [HomeSampleData] 로 고정이다
 * → 값을 통째로 베껴 비교하지 않고 "무엇이 참이어야 하는가" 로 단언한다
 * (상한 3·1 두 건만 샘플 구성에 기대며, 그 전제를 테스트 안에서 먼저 확인한다)
 */
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    /**
     * `SharingStarted.Eagerly` 라도 [kotlinx.coroutines.test.StandardTestDispatcher] 아래선
     * combine 코루틴이 큐에 쌓인 채다 — 실행시킨 뒤에 읽어야 파생값이 보인다
     */
    private fun TestScope.uiStateAfterIdle(): HomeUiState {
        advanceUntilIdle()
        return viewModel.uiState.value
    }

    // ---------------------------------------------------------------------
    // uiState 노출 시점 — stateIn 초기값 성질
    // ---------------------------------------------------------------------

    @Test
    fun `uiState는 combine이 실행되기 전까지 stateIn 초기값을 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // 회귀 방어 — "테스트가 느리다" 며 advanceUntilIdle() 을 지우면
            // 아래 두 단언이 자리를 바꿔 실패한다
            assertEquals(HomeUiState(), viewModel.uiState.value)

            advanceUntilIdle()

            assertNotEquals(HomeUiState(), viewModel.uiState.value)
        }

    // ---------------------------------------------------------------------
    // recentRecords — 위시리스트 제외 + 상한
    // ---------------------------------------------------------------------

    @Test
    fun `recentRecords에는 가보고싶어요 기록이 섞이지 않는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val recentRecords = uiStateAfterIdle().recentRecords

            // 비어 있으면 아래 none 단언이 공허하게 통과한다
            assertTrue(recentRecords.isNotEmpty())
            assertTrue(recentRecords.none { it.evaluation == StashEvalState.WantToTry })
        }

    @Test
    fun `recentRecords는 가보고싶어요를 뺀 기록을 3건까지만 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // 샘플 구성 전제 — 후보가 상한보다 많아야 take 가 실제로 깎는지 알 수 있다
            val candidateCount = HomeSampleData.records
                .count { it.evaluation != StashEvalState.WantToTry }
            assertTrue("샘플의 비위시리스트 기록이 3건 이하면 상한을 검증할 수 없다", candidateCount > 3)

            assertEquals(3, uiStateAfterIdle().recentRecords.size)
        }

    // ---------------------------------------------------------------------
    // wishlistRecords — 가보고싶어요만 + 상한
    // ---------------------------------------------------------------------

    @Test
    fun `wishlistRecords는 가보고싶어요 기록만 담는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val wishlistRecords = uiStateAfterIdle().wishlistRecords

            assertTrue(wishlistRecords.isNotEmpty())
            assertTrue(wishlistRecords.all { it.evaluation == StashEvalState.WantToTry })
        }

    @Test
    fun `wishlistRecords는 1건까지만 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // 샘플 구성 전제 — 가보고싶어요가 2건 이상이어야 상한이 의미를 갖는다
            val candidateCount = HomeSampleData.records
                .count { it.evaluation == StashEvalState.WantToTry }
            assertTrue("샘플의 가보고싶어요 기록이 1건 이하면 상한을 검증할 수 없다", candidateCount > 1)

            assertEquals(1, uiStateAfterIdle().wishlistRecords.size)
        }

    // ---------------------------------------------------------------------
    // stats · monthlySummary — 가공 없는 전달
    // ---------------------------------------------------------------------

    @Test
    fun `stats와 monthlySummary는 가공 없이 그대로 전달된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val uiState = uiStateAfterIdle()

            assertEquals(HomeSampleData.stats, uiState.stats)
            assertEquals(HomeSampleData.monthlySummary, uiState.monthlySummary)
        }
}
