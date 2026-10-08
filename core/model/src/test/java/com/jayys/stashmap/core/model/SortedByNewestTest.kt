package com.jayys.stashmap.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.time.LocalDate

/**
 * [sortedByNewest] 단위 테스트
 *
 * 미방문(null)이 뒤로 가는 건 `compareByDescending` 단일 인자 오버로드의 기본 동작이다
 * → 홈·맛집 경유 간접 검증에 맡기지 않고 여기서 직접 고정한다
 */
class SortedByNewestTest {

    @Test
    fun `방문일이 늦은 기록부터 나온다`() {
        // 입력을 일부러 날짜순이 아니게 둔다 — 정렬을 지워도 통과하면 안 된다
        val records = listOf(
            record("b", LocalDate.parse("2026-08-14")),
            record("a", LocalDate.parse("2026-09-28")),
            record("c", LocalDate.parse("2026-09-05")),
        )

        val sorted = records.sortedByNewest()

        // 전제 — 입력이 이미 정렬돼 있으면 정렬을 지워도 통과한다
        assertNotEquals(records.map { it.id }, sorted.map { it.id })
        assertEquals(listOf("a", "c", "b"), sorted.map { it.id })
    }

    @Test
    fun `미방문 기록은 방문 기록보다 뒤에 온다`() {
        val records = listOf(
            record("w1", null),
            record("v", LocalDate.parse("2026-06-18")),
            record("w2", null),
        )

        val sorted = records.sortedByNewest()

        assertEquals("v", sorted.first().id)
        assertEquals(listOf(null, null), sorted.drop(1).map { it.visitedAt })
    }

    @Test
    fun `같은 날짜끼리는 원래 순서가 유지된다`() {
        val sameDay = LocalDate.parse("2026-07-30")
        val records = listOf(record("first", sameDay), record("second", sameDay))

        assertEquals(listOf("first", "second"), records.sortedByNewest().map { it.id })
    }

    @Test
    fun `빈 목록도 그대로 비어 있다`() {
        assertEquals(emptyList<RestaurantRecord>(), emptyList<RestaurantRecord>().sortedByNewest())
    }

    private fun record(id: String, visitedAt: LocalDate?) = RestaurantRecord(
        id = id,
        name = id,
        evaluation = Evaluation.Favorite,
        category = "",
        area = "",
        memo = "",
        visitedAt = visitedAt,
    )
}
