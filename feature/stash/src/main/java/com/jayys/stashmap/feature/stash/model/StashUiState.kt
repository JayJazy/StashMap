package com.jayys.stashmap.feature.stash.model

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.EvaluationCounts
import java.time.LocalDate

/**
 * 맛집 탭 화면 상태
 *
 * [counts] 는 필터 적용 **전** 전체 기준 — 필터를 걸어도 그리드 숫자가 그대로여야 다른 칸으로 옮겨갈 수 있다
 */
data class StashUiState(
    val isLoading: Boolean = true,
    val records: List<StashRecordCard> = emptyList(),
    val counts: EvaluationCounts = EvaluationCounts(),
    val selectedEvaluation: Evaluation? = null,
    val sortOrder: StashSortOrder = StashSortOrder.Newest,
)

/**
 * 카드 한 장에 필요한 만큼만 — 표시 문자열은 여기 들어오기 전에 이미 만들어져 있다
 *
 * [visitedAt] 은 [meta] 안에 녹아 있어 그릴 때 쓰지 않는다. 정렬 계약을 이 경계에서 단언하려고 들고 간다
 */
data class StashRecordCard(
    val id: String,
    val name: String,
    val evaluation: Evaluation,
    val visitedAt: LocalDate?,
    val meta: String?,
    val memo: String?,
)

/** 정렬 기준 — 아직 최신순 하나뿐 */
enum class StashSortOrder {
    Newest,
}
