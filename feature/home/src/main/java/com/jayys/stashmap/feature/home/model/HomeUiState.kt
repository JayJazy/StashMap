package com.jayys.stashmap.feature.home.model

/**
 * 홈 대시보드 화면 상태
 *
 * 모든 필드가 기본값을 가져 `HomeUiState()` 만으로 빈 화면이 그려진다 (초기값·Preview 용)
 */
data class HomeUiState(
    val recentRecords: List<HomeRecord> = emptyList(),
    val wishlistRecords: List<HomeRecord> = emptyList(),
    val stats: HomeEvalStats = HomeEvalStats(),
    val monthlySummary: HomeMonthlySummary = HomeMonthlySummary(),
)

/** 평가 4상태별 누적 개수 — 상단 1×4 통계 줄 */
data class HomeEvalStats(
    val favorite: Int = 0,
    val average: Int = 0,
    val avoid: Int = 0,
    val wantToTry: Int = 0,
)

/** 이번 달 요약 — 기록한 곳 / 그중 다시 갈 곳 */
data class HomeMonthlySummary(
    val recordedCount: Int = 0,
    val revisitCount: Int = 0,
)
