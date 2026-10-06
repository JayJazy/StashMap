package com.jayys.stashmap.feature.stash.viewmodel

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import com.jayys.stashmap.feature.stash.model.StashUiState
import com.jayys.stashmap.feature.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * [StashViewModel] 단위 테스트
 *
 * 저장소가 없어 주입할 더블도 없고 입력이 [SampleRecords] 로 고정이다
 * → 값을 통째로 베껴 비교하지 않고 "무엇이 참이어야 하는가" 로 단언한다
 * (샘플 구성에 기대는 전제는 테스트 안에서 먼저 확인한다)
 *
 * `uiState` 는 `SharingStarted.WhileSubscribed` → **구독자가 없으면 combine 이 돌지 않는다.**
 * `uiState.value` 를 읽는 건 구독이 아니므로 [startCollectingUiState] 로 수집을 먼저 걸어야 한다
 */
@OptIn(ExperimentalCoroutinesApi::class)
class StashViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: StashViewModel

    @Before
    fun setUp() {
        viewModel = StashViewModel()
    }

    /** 테스트 종료 시 자동 취소되는 [TestScope.backgroundScope] 에서 수집 시작 */
    private fun TestScope.startCollectingUiState() {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect { }
        }
    }

    /** 이미 구독 중일 때, 클릭 등 액션이 combine 을 통과한 뒤의 상태 */
    private fun TestScope.uiStateNow(): StashUiState {
        advanceUntilIdle()
        return viewModel.uiState.value
    }

    /** 구독을 걸고 combine 이 돌 때까지 진행시킨 뒤의 상태 */
    private fun TestScope.uiStateAfterCollect(): StashUiState {
        startCollectingUiState()
        return uiStateNow()
    }

    // ---------------------------------------------------------------------
    // uiState 노출 시점 — WhileSubscribed 계약
    // ---------------------------------------------------------------------

    @Test
    fun `uiState는 구독자가 생기기 전까지 stateIn 초기값을 노출한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            // 아무도 보지 않으면 시간이 아무리 흘러도 combine 이 돌지 않는다
            advanceUntilIdle()
            assertEquals(StashUiState(), viewModel.uiState.value)

            startCollectingUiState()
            advanceUntilIdle()

            assertNotEquals(StashUiState(), viewModel.uiState.value)
        }

    @Test
    fun `구독이 걸리면 isLoading이 내려가고 목록이 채워진다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            assertTrue("구독 전 초기값은 로딩 상태여야 한다", viewModel.uiState.value.isLoading)

            val uiState = uiStateAfterCollect()

            assertFalse(uiState.isLoading)
            assertTrue(uiState.records.isNotEmpty())
        }

    // ---------------------------------------------------------------------
    // records — 평가 상태 필터
    // ---------------------------------------------------------------------
    // 계획의 "결과 0건인 필터" 케이스는 뺐다 — 현재 샘플은 4개 상태가 모두 1건 이상이라
    // 빈 목록을 만들 수 있는 평가 상태가 없다 (프로덕션 샘플 수정은 범위 밖)

    @Test
    fun `필터를 걸지 않으면 전체 기록이 그대로 노출된다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val uiState = uiStateAfterCollect()

            assertNull(uiState.selectedEvaluation)
            assertEquals(SampleRecords.records.size, uiState.records.size)
            assertEquals(SampleRecords.records.toSet(), uiState.records.toSet())
            // 홈 탭과 달리 맛집 탭은 가보고싶어요도 같이 보여준다
            assertTrue(uiState.records.any { it.evaluation == Evaluation.WantToTry })
        }

    @Test
    fun `상태를 고르면 그 상태의 기록만 남는다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()

            // 서로 다른 상태를 연달아 누르므로 토글 해제는 일어나지 않는다
            Evaluation.entries.forEach { target ->
                viewModel.onEvaluationFilterClick(target)
                val uiState = uiStateNow()

                val expected = SampleRecords.records.filter { it.evaluation == target }
                // 비어 있으면 아래 all 단언이 공허하게 통과한다
                assertTrue("샘플에 $target 기록이 없어 필터를 검증할 수 없다", expected.isNotEmpty())

                assertEquals(target, uiState.selectedEvaluation)
                assertEquals(expected.toSet(), uiState.records.toSet())
                assertTrue(uiState.records.all { it.evaluation == target })
            }
        }

    @Test
    fun `같은 상태를 다시 누르면 필터가 풀리고 전체로 돌아온다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()

            viewModel.onEvaluationFilterClick(Evaluation.Favorite)
            val filtered = uiStateNow()

            assertEquals(Evaluation.Favorite, filtered.selectedEvaluation)
            // 필터가 실제로 깎아야 "풀린다" 가 의미를 갖는다
            assertTrue(filtered.records.size < SampleRecords.records.size)

            viewModel.onEvaluationFilterClick(Evaluation.Favorite)
            val cleared = uiStateNow()

            assertNull(cleared.selectedEvaluation)
            assertEquals(SampleRecords.records.size, cleared.records.size)
        }

    // ---------------------------------------------------------------------
    // counts — 그리드는 필터와 무관하게 전체를 센다
    // ---------------------------------------------------------------------

    @Test
    fun `counts는 필터를 걸어도 전체 기준을 유지한다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            startCollectingUiState()
            val unfiltered = uiStateNow()

            viewModel.onEvaluationFilterClick(Evaluation.Favorite)
            val filtered = uiStateNow()

            // 목록은 줄었는데 숫자는 그대로여야 다른 칸으로 옮겨갈 수 있다
            assertTrue(filtered.records.size < unfiltered.records.size)
            assertEquals(unfiltered.counts, filtered.counts)
            assertEquals(SampleRecords.records.evaluationCounts(), filtered.counts)
        }

    @Test
    fun `counts의 합은 전체 기록 수와 같다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val counts = uiStateAfterCollect().counts

            val total = counts.favorite + counts.average + counts.avoid + counts.wantToTry
            assertEquals(SampleRecords.records.size, total)
        }

    // ---------------------------------------------------------------------
    // 정렬 — 최신순, 미방문은 뒤
    // ---------------------------------------------------------------------

    @Test
    fun `records는 방문일 내림차순이고 미방문 기록은 뒤로 간다`() =
        runTest(mainDispatcherRule.testDispatcher) {
            val visitedAts = uiStateAfterCollect().records.map { it.visitedAt }
            val dated = visitedAts.filterNotNull()

            // 샘플 구성 전제 — 날짜가 2건 이상이어야 순서를, 미방문이 있어야 null 위치를 본다
            assertTrue("샘플의 방문일 있는 기록이 2건 미만이면 정렬 순서를 검증할 수 없다", dated.size > 1)
            assertTrue("샘플에 미방문 기록이 없으면 null 위치를 검증할 수 없다", dated.size < visitedAts.size)

            assertEquals(dated.sortedDescending(), dated)
            // null 이 전부 뒤에 몰렸다면 앞쪽 dated.size 칸이 그대로 날짜 있는 기록이다
            assertEquals(dated, visitedAts.take(dated.size))
        }
}
