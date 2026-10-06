package com.jayys.stashmap.core.model.sample

import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.RestaurantRecord

// TODO: 기록 저장소(core:data)가 붙으면 통째로 삭제 — 그때까진 화면 확인용 임시 데이터
object SampleRecords {

    val records: List<RestaurantRecord> = listOf(
        RestaurantRecord(
            id = "r1",
            name = "마루 비스트로",
            evaluation = Evaluation.Favorite,
            category = "양식",
            area = "성수동",
            distance = "1.2km",
            memo = "여기 파스타 짱. 창가 자리 추천.",
            visitedAt = "2026.09.28",
        ),
        RestaurantRecord(
            id = "r2",
            name = "포어오버 랩",
            evaluation = Evaluation.Favorite,
            category = "카페·디저트",
            area = "연남동",
            distance = "3.2km",
            memo = "싱글 오리진이 다양하고 조도가 좋다.",
            visitedAt = "2026.08.14",
        ),
        RestaurantRecord(
            id = "r3",
            name = "청담 화로",
            evaluation = Evaluation.Favorite,
            category = "고기·구이",
            area = "청담동",
            distance = "6.1km",
            memo = "기념일에 다시 오고 싶은 곳. 한우 등심.",
            visitedAt = "2026.09.12",
        ),
        RestaurantRecord(
            id = "r4",
            name = "그린볼",
            evaluation = Evaluation.Average,
            category = "양식",
            area = "서촌",
            distance = "2.1km",
            memo = "점심 샐러드 세트는 무난. 회전 빠름.",
            visitedAt = "2026.07.30",
        ),
        RestaurantRecord(
            id = "r5",
            name = "미도 분식",
            evaluation = Evaluation.Average,
            category = "한식",
            area = "망원동",
            distance = "4.4km",
            memo = "떡볶이는 평범, 김밥은 괜찮다.",
            visitedAt = "2026.09.05",
        ),
        RestaurantRecord(
            id = "r6",
            name = "버거 스택",
            evaluation = Evaluation.Avoid,
            category = "패스트푸드·버거",
            area = "합정동",
            distance = "3.8km",
            memo = "패티가 너무 짜고 식어서 왔다.",
            visitedAt = "2026.06.18",
        ),
        RestaurantRecord(
            id = "r7",
            name = "오션 테이블",
            evaluation = Evaluation.WantToTry,
            category = "해산물",
            area = "한남동",
            distance = "5.8km",
            memo = "굴 코스가 유명하다던데.",
            visitedAt = null,
        ),
        RestaurantRecord(
            id = "r8",
            name = "사쿠라 스시",
            evaluation = Evaluation.WantToTry,
            category = "일식",
            area = "삼성동",
            distance = "7.0km",
            memo = "오마카세 점심 예약 열린다고.",
            visitedAt = null,
        ),
        RestaurantRecord(
            id = "r9",
            name = "르뱅 베이커리",
            evaluation = Evaluation.WantToTry,
            category = "베이커리",
            area = "연희동",
            distance = "4.0km",
            memo = "캄파뉴가 인상적이라는 후기.",
            visitedAt = null,
        ),
    )
}
