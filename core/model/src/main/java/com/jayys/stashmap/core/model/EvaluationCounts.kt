package com.jayys.stashmap.core.model

/** 평가 4상태별 개수 */
data class EvaluationCounts(
    val favorite: Int = 0,
    val average: Int = 0,
    val avoid: Int = 0,
    val wantToTry: Int = 0,
)

/** 기록 목록에서 상태별 개수를 집계 */
fun List<RestaurantRecord>.evaluationCounts(): EvaluationCounts {
    val counts = groupingBy { it.evaluation }.eachCount()
    return EvaluationCounts(
        favorite = counts[Evaluation.Favorite] ?: 0,
        average = counts[Evaluation.Average] ?: 0,
        avoid = counts[Evaluation.Avoid] ?: 0,
        wantToTry = counts[Evaluation.WantToTry] ?: 0,
    )
}
