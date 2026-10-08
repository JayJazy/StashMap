package com.jayys.stashmap.feature.home.viewmodel

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.RestaurantRecord
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

/**
 * [RestaurantRecord.toCard] 단위 테스트
 *
 * [HomeViewModelTest] 는 `recentRecords`·`wishlistRecords` 를 평가 상태·개수·날짜로만 본다
 * → meta·memo 내용은 무조건 통과하고, category 와 area 를 맞바꿔도 녹색이다
 * 여기서만 필드 배선과 meta 조립을 고정한다 (입력을 전부 다른 값으로 줘 스왑이 드러나게)
 *
 * 맛집에 같은 매퍼가 있어 이 파일도 쌍둥이다 — 포맷을 바꾸면 양쪽 VM 과 양쪽 테스트 네 군데
 */
class ToCardTest {

    @Test
    fun `모든 필드가 제자리로 간다`() {
        // 필드끼리 뒤바뀌면 값이 달라져 드러나도록 입력을 서로 겹치지 않게 둔다
        val card = record(
            id = "r42",
            name = "마루 비스트로",
            evaluation = Evaluation.Avoid,
            category = "양식",
            area = "성수동",
            memo = "창가 자리 추천",
            visitedAt = LocalDate.parse("2026-09-28"),
        ).toCard()

        assertEquals("r42", card.id)
        assertEquals("마루 비스트로", card.name)
        assertEquals(Evaluation.Avoid, card.evaluation)
        assertEquals(LocalDate.parse("2026-09-28"), card.visitedAt)
        assertEquals("창가 자리 추천", card.memo)
        // 카테고리 · 지역 순서 — 둘이 바뀌면 여기서 걸린다
        assertEquals("양식 · 성수동", card.meta)
    }

    @Test
    fun `방문일은 카드로 넘어가되 meta 에는 안 들어간다`() {
        // 방문일 표시는 Phase 4 로 미뤘다 — meta 에 넣으면 10자가 앞을 먹어 지역이 잘린다
        val visited = record(category = "일식", area = "삼성동", visitedAt = LocalDate.parse("2026-09-28")).toCard()
        val notVisited = record(category = "일식", area = "삼성동", visitedAt = null).toCard()

        assertEquals("일식 · 삼성동", visited.meta)
        assertEquals("일식 · 삼성동", notVisited.meta)
        assertEquals(LocalDate.parse("2026-09-28"), visited.visitedAt)
        assertNull(notVisited.visitedAt)
    }

    @Test
    fun `빈 세그먼트는 meta에서 걸러진다`() {
        // 공백뿐인 값까지 빼야 구분자만 남은 줄이 안 생긴다
        assertEquals("서촌", record(category = "").toCard().meta)
        assertEquals("한식", record(area = "   ").toCard().meta)
    }

    @Test
    fun `세그먼트가 전부 비면 meta는 null이다`() {
        assertNull(record(category = "", area = "").toCard().meta)
    }

    @Test
    fun `비어 있거나 공백뿐인 memo는 null이 된다`() {
        assertNull(record(memo = "").toCard().memo)
        assertNull(record(memo = "   ").toCard().memo)
    }

    private fun record(
        id: String = "r1",
        name: String = "가게 이름",
        evaluation: Evaluation = Evaluation.Favorite,
        category: String = "한식",
        area: String = "서촌",
        memo: String = "메모 본문",
        visitedAt: LocalDate? = LocalDate.parse("2026-07-30"),
    ) = RestaurantRecord(
        id = id,
        name = name,
        evaluation = evaluation,
        category = category,
        area = area,
        memo = memo,
        visitedAt = visitedAt,
    )
}
