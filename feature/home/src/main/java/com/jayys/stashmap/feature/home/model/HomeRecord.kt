package com.jayys.stashmap.feature.home.model

import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState

/**
 * 홈 대시보드가 보여주는 가게 기록 한 건
 *
 * 평가에 DS 의 [StashEvalState] 를 그대로 쓴다 — 임시 데이터 단계라 매핑을 미뤄둔 것.
 * core:domain 이 순수 JVM 이라 DS 에 의존할 수 없어, 저장소가 붙으면 core:model 에 평가 enum + 매퍼가 필요하다
 */
data class HomeRecord(
    val id: String,
    val name: String,
    val evaluation: StashEvalState,
    val category: String,
    val area: String,
    val distance: String,
    val memo: String,
)
