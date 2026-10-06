package com.jayys.stashmap.core.model

/**
 * 가게 기록 한 건 — 홈·맛집 탭이 함께 쓰는 공유 모델
 *
 * @param visitedAt 표시용 방문일 `yyyy.MM.dd`, 미방문이면 null.
 *   zero-padded 라 사전순 비교가 곧 시간순 비교 — 포맷을 바꾸면 정렬이 조용히 깨진다
 */
data class RestaurantRecord(
    val id: String,
    val name: String,
    val evaluation: Evaluation,
    val category: String,
    val area: String,
    val distance: String,
    val memo: String,
    val visitedAt: String?,
)

/** 최신 방문 순, 미방문은 뒤로 — 홈 "최근 기록"과 맛집 "최신순"이 같은 순서를 보장하려면 둘 다 이걸 써야 한다 */
fun List<RestaurantRecord>.sortedByNewest(): List<RestaurantRecord> = sortedWith(NewestFirst)

// compareByDescending 은 인자를 뒤집어 넘기므로 nullsFirst 가 실제로는 null 을 뒤로 보낸다
private val NewestFirst =
    compareByDescending<RestaurantRecord, String?>(nullsFirst()) { it.visitedAt }
