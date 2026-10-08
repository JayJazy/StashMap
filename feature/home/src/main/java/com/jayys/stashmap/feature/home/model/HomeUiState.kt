package com.jayys.stashmap.feature.home.model

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.EvaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import java.time.LocalDate

/**
 * 홈 대시보드 화면 상태
 *
 * 모든 필드가 기본값을 가져 `HomeUiState()` 만으로 빈 화면이 그려진다 (초기값·Preview 용)
 */
data class HomeUiState(
    val recentRecords: List<HomeRecordCard> = emptyList(),
    val wishlistRecords: List<HomeRecordCard> = emptyList(),
    val stats: EvaluationCounts = EvaluationCounts(),
    val monthlySummary: HomeMonthlySummary = HomeMonthlySummary(),
)

/**
 * 카드 한 장에 필요한 만큼만 — 표시 문자열은 여기 들어오기 전에 이미 만들어져 있다
 *
 * [visitedAt] 은 [meta] 안에 녹아 있어 그릴 때 쓰지 않는다. 정렬 계약을 이 경계에서 단언하려고 들고 간다
 */
data class HomeRecordCard(
    val id: String,
    val name: String,
    val evaluation: Evaluation,
    val visitedAt: LocalDate?,
    val meta: String?,
    val memo: String?,
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
