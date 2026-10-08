package com.jayys.stashmap.core.model

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [evaluationCounts] 단위 테스트
 *
 * ViewModel 테스트는 `uiState.counts` 를 `records.evaluationCounts()` 와 비교한다
 * → 이 함수를 자기 자신과 맞대보는 셈이라 상태↔필드 배선이 어긋나도 못 잡는다
 * 여기서만 상태별 개수를 전부 다르게 준 입력으로 그 배선을 고정한다
 */
class EvaluationCountsTest {

    // 개수를 4·3·2·1 로 흩어 놓음 — 필드끼리 뒤바뀌면 값이 달라져 드러난다
    private val records = buildList {
        repeat(4) { add(record(Evaluation.Favorite, "f$it")) }
        repeat(3) { add(record(Evaluation.Average, "m$it")) }
        repeat(2) { add(record(Evaluation.Avoid, "x$it")) }
        add(record(Evaluation.WantToTry, "w0"))
    }

    @Test
    fun `상태별 개수가 각자의 필드에 담긴다`() {
        val counts = records.evaluationCounts()

        assertEquals(records.count { it.evaluation == Evaluation.Favorite }, counts.favorite)
        assertEquals(records.count { it.evaluation == Evaluation.Average }, counts.average)
        assertEquals(records.count { it.evaluation == Evaluation.Avoid }, counts.avoid)
        assertEquals(records.count { it.evaluation == Evaluation.WantToTry }, counts.wantToTry)
    }

    @Test
    fun `목록에 없는 상태는 0으로 집계된다`() {
        val onlyFavorite = records.filter { it.evaluation == Evaluation.Favorite }

        val counts = onlyFavorite.evaluationCounts()

        assertEquals(EvaluationCounts(favorite = onlyFavorite.size), counts)
    }

    @Test
    fun `빈 목록은 전부 0이다`() {
        assertEquals(EvaluationCounts(), emptyList<RestaurantRecord>().evaluationCounts())
    }

    private fun record(evaluation: Evaluation, id: String) = RestaurantRecord(
        id = id,
        name = id,
        evaluation = evaluation,
        category = "",
        area = "",
        memo = "",
        visitedAt = null,
    )
}
