package com.jayys.stashmap.feature.home.model

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.EvaluationCounts
import com.jayys.stashmap.core.model.RestaurantRecord
import com.jayys.stashmap.core.model.sample.SampleRecords

/**
 * 홈 대시보드 화면 상태
 *
 * 모든 필드가 기본값을 가져 `HomeUiState()` 만으로 빈 화면이 그려진다 (초기값·Preview 용)
 */
data class HomeUiState(
    val recentRecords: List<RestaurantRecord> = emptyList(),
    val wishlistRecords: List<RestaurantRecord> = emptyList(),
    val stats: EvaluationCounts = EvaluationCounts(),
    val monthlySummary: HomeMonthlySummary = HomeMonthlySummary(),
)

/** 이번 달 요약 — 기록한 곳 / 그중 다시 갈 곳 */
data class HomeMonthlySummary(
    val recordedCount: Int = 0,
    val revisitCount: Int = 0,
)

// TODO: 기록 저장소(core:data)가 붙으면 삭제 — "이번 달"을 가를 기준이 아직 없어 방문 기록 전체에서 센다
//  문구가 "그중 다시 갈 집"이라 두 수의 모집단(= 방문한 기록)이 같아야 한다
internal val SampleMonthlySummary = HomeMonthlySummary(
    recordedCount = SampleRecords.records.count { it.visitedAt != null },
    revisitCount = SampleRecords.records
        .count { it.visitedAt != null && it.evaluation == Evaluation.Favorite },
)
