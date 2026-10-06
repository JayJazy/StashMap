package com.jayys.stashmap.feature.stash.model

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.EvaluationCounts
import com.jayys.stashmap.core.model.RestaurantRecord

/**
 * 맛집 탭 화면 상태
 *
 * [counts] 는 필터 적용 **전** 전체 기준 — 필터를 걸어도 그리드 숫자가 그대로여야 다른 칸으로 옮겨갈 수 있다
 */
data class StashUiState(
    val isLoading: Boolean = true,
    val records: List<RestaurantRecord> = emptyList(),
    val counts: EvaluationCounts = EvaluationCounts(),
    val selectedEvaluation: Evaluation? = null,
    val sortOrder: StashSortOrder = StashSortOrder.Newest,
)

/** 정렬 기준 — 아직 최신순 하나뿐 */
enum class StashSortOrder {
    Newest,
}
