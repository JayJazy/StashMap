package com.jayys.stashmap.feature.home.viewmodel

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import com.jayys.stashmap.feature.home.model.HomeUiState
import com.jayys.stashmap.feature.home.model.SampleMonthlySummary
import com.jayys.stashmap.feature.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
 * 저장소가 없어 주입할 더블도 없고 입력이 [SampleRecords] 로 고정이다
 * → 값을 통째로 베껴 비교하지 않고 "무엇이 참이어야 하는가" 로 단언한다
 * (상한 3·1 두 건만 샘플 구성에 기대며, 그 전제를 테스트 안에서 먼저 확인한다)
 *
 * `uiState` 는 `SharingStarted.WhileSubscribed` → **구독자가 없으면 combine 이 돌지 않는다.**
 * `uiState.value` 를 읽는 건 구독이 아니므로 [startCollectingUiState] 로 수집을 먼저 걸어야 한다
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

    /** 테스트 종료 시 자동 취소되는 [TestScope.backgroundScope] 에서 수집 시작 */
    private fun TestScope.startCollectingUiState() {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect { }
        }
    }

    /** 구독을 걸고 combine 이 돌 때까지 진행시킨 뒤의 상태 */
    private fun TestScope.uiStateAfterCollect(): HomeUiState {
        startCollectingUiState()
        advanceUntilIdle()
        return viewModel.uiState.value
    }

    // ---------------------------------------------------------------------
    // uiState 노출 시점 — WhileSubscribed 계약
    // ---------------------------------------------------------------------

    @Test
    fun `uiState는 구독자가 생기기 전까지 stateIn 초기값을 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // 아무도 보지 않으면 시간이 아무리 흘러도 combine 이 돌지 않는다
            advanceUntilIdle()
            assertEquals(HomeUiState(), viewModel.uiState.value)

            startCollectingUiState()
            advanceUntilIdle()

            assertNotEquals(HomeUiState(), viewModel.uiState.value)
        }

    // ---------------------------------------------------------------------
    // recentRecords — 위시리스트 제외 + 상한
    // ---------------------------------------------------------------------

    @Test
    fun `recentRecords에는 가보고싶어요 기록이 섞이지 않는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val recentRecords = uiStateAfterCollect().recentRecords

            // 비어 있으면 아래 none 단언이 공허하게 통과한다
            assertTrue(recentRecords.isNotEmpty())
            assertTrue(recentRecords.none { it.evaluation == Evaluation.WantToTry })
        }

    @Test
    fun `recentRecords는 가보고싶어요를 뺀 기록을 3건까지만 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // 샘플 구성 전제 — 후보가 상한보다 많아야 take 가 실제로 깎는지 알 수 있다
            val candidateCount = SampleRecords.records
                .count { it.evaluation != Evaluation.WantToTry }
            assertTrue("샘플의 비위시리스트 기록이 3건 이하면 상한을 검증할 수 없다", candidateCount > 3)

            assertEquals(3, uiStateAfterCollect().recentRecords.size)
        }

    @Test
    fun `recentRecords는 방문일이 늦은 순서로 노출된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val recentRecords = uiStateAfterCollect().recentRecords
            val shownDates = recentRecords.mapNotNull { it.visitedAt }
            assertTrue(shownDates.isNotEmpty())

            // 노출된 것끼리 내림차순
            assertEquals(shownDates.sortedDescending(), shownDates)

            // "최근"이려면 상한을 자르기 전에 정렬이 끝나야 한다 — 탈락한 기록이 더 늦으면 안 된다
            val shownIds = recentRecords.map { it.id }.toSet()
            val droppedDates = SampleRecords.records
                .filterNot { it.evaluation == Evaluation.WantToTry || it.id in shownIds }
                .mapNotNull { it.visitedAt }
            assertTrue("탈락 후보가 없으면 정렬 여부를 구분할 수 없다", droppedDates.isNotEmpty())
            assertTrue(droppedDates.all { it <= shownDates.last() })
        }

    // ---------------------------------------------------------------------
    // wishlistRecords — 가보고싶어요만 + 상한
    // ---------------------------------------------------------------------

    @Test
    fun `wishlistRecords는 가보고싶어요 기록만 담는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val wishlistRecords = uiStateAfterCollect().wishlistRecords

            assertTrue(wishlistRecords.isNotEmpty())
            assertTrue(wishlistRecords.all { it.evaluation == Evaluation.WantToTry })
        }

    @Test
    fun `wishlistRecords는 1건까지만 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // 샘플 구성 전제 — 가보고싶어요가 2건 이상이어야 상한이 의미를 갖는다
            val candidateCount = SampleRecords.records
                .count { it.evaluation == Evaluation.WantToTry }
            assertTrue("샘플의 가보고싶어요 기록이 1건 이하면 상한을 검증할 수 없다", candidateCount > 1)

            assertEquals(1, uiStateAfterCollect().wishlistRecords.size)
        }

    // ---------------------------------------------------------------------
    // stats · monthlySummary — 전체 기록 기준 파생 / 그대로 전달
    // ---------------------------------------------------------------------

    @Test
    fun `stats는 전체 기록에서 파생되고 monthlySummary는 그대로 전달된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val uiState = uiStateAfterCollect()

            assertEquals(SampleRecords.records.evaluationCounts(), uiState.stats)
            assertEquals(SampleMonthlySummary, uiState.monthlySummary)
        }

    @Test
    fun `monthlySummary의 다시 갈 집은 기록한 곳의 부분집합이다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // "그중 다시 갈 집" 문구대로 두 수의 모집단이 같아야 한다 (둘 다 방문한 기록 기준)
            val summary = uiStateAfterCollect().monthlySummary

            assertTrue(summary.recordedCount > 0)
            assertTrue(summary.revisitCount > 0)
            assertTrue(
                "다시 갈 집(${summary.revisitCount})이 기록한 곳(${summary.recordedCount})보다 많을 수 없다",
                summary.revisitCount <= summary.recordedCount,
            )
            // 미방문 기록은 어느 쪽에도 안 들어간다
            assertEquals(SampleRecords.records.count { it.visitedAt != null }, summary.recordedCount)
        }
}
