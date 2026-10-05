package com.jayys.stashmap.feature.home.model

import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState

// TODO: 기록 저장소(core:data)가 붙으면 통째로 삭제 — 그때까진 홈 레이아웃 확인용 임시 데이터
internal object HomeSampleData {

    val records: List<HomeRecord> = listOf(
        HomeRecord(
            id = "r1",
            name = "마루 비스트로",
            evaluation = StashEvalState.Favorite,
            category = "양식",
            area = "성수동",
            distance = "1.2km",
            memo = "여기 파스타 짱. 창가 자리 추천.",
        ),
        HomeRecord(
            id = "r2",
            name = "포어오버 랩",
            evaluation = StashEvalState.Favorite,
            category = "카페·디저트",
            area = "연남동",
            distance = "3.2km",
            memo = "싱글 오리진이 다양하고 조도가 좋다.",
        ),
        HomeRecord(
            id = "r3",
            name = "청담 화로",
            evaluation = StashEvalState.Favorite,
            category = "고기·구이",
            area = "청담동",
            distance = "6.1km",
            memo = "기념일에 다시 오고 싶은 곳. 한우 등심.",
        ),
        HomeRecord(
            id = "r4",
            name = "그린볼",
            evaluation = StashEvalState.Average,
            category = "양식",
            area = "서촌",
            distance = "2.1km",
            memo = "점심 샐러드 세트는 무난. 회전 빠름.",
        ),
        HomeRecord(
            id = "r5",
            name = "미도 분식",
            evaluation = StashEvalState.Average,
            category = "한식",
            area = "망원동",
            distance = "4.4km",
            memo = "떡볶이는 평범, 김밥은 괜찮다.",
        ),
        HomeRecord(
            id = "r6",
            name = "버거 스택",
            evaluation = StashEvalState.Avoid,
            category = "패스트푸드·버거",
            area = "합정동",
            distance = "3.8km",
            memo = "패티가 너무 짜고 식어서 왔다.",
        ),
        HomeRecord(
            id = "r7",
            name = "오션 테이블",
            evaluation = StashEvalState.WantToTry,
            category = "해산물",
            area = "한남동",
            distance = "5.8km",
            memo = "굴 코스가 유명하다던데.",
        ),
        HomeRecord(
            id = "r8",
            name = "사쿠라 스시",
            evaluation = StashEvalState.WantToTry,
            category = "일식",
            area = "삼성동",
            distance = "7.0km",
            memo = "오마카세 점심 예약 열린다고.",
        ),
        HomeRecord(
            id = "r9",
            name = "르뱅 베이커리",
            evaluation = StashEvalState.WantToTry,
            category = "베이커리",
            area = "연희동",
            distance = "4.0km",
            memo = "캄파뉴가 인상적이라는 후기.",
        ),
    )

    val stats: HomeEvalStats = HomeEvalStats(
        favorite = 12,
        average = 5,
        avoid = 3,
        wantToTry = 8,
    )

    val monthlySummary: HomeMonthlySummary = HomeMonthlySummary(
        recordedCount = 8,
        revisitCount = 3,
    )
}
