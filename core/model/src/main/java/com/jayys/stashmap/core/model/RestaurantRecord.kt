package com.jayys.stashmap.core.model

import java.time.LocalDate

/** 가게 기록 한 건 — 홈·맛집 탭이 함께 쓰는 공유 모델 */
data class RestaurantRecord(
    val id: String,
    val name: String,
    val evaluation: Evaluation,
    val category: String,
    val area: String,
    val memo: String,
    val visitedAt: LocalDate?,
    val latitude: Double? = null,
    val longitude: Double? = null,
)

/** 최신 방문 순, 미방문은 뒤로 — 홈 "최근 기록"과 맛집 "최신순"이 같은 순서를 보장하려면 둘 다 이걸 써야 한다 */
fun List<RestaurantRecord>.sortedByNewest(): List<RestaurantRecord> = sortedWith(NewestFirst)

private val NewestFirst = compareByDescending<RestaurantRecord> { it.visitedAt }
