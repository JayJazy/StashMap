package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Stash Design System 의 아이콘 크기 토큰
 *
 * 간격([StashSpacing])과 달리 4dp 그리드를 따르지 않음 — 아이콘은 광학 크기로 고름
 */
object StashIconSize {
    /** 작은 버튼·칩 안 */
    val xs: Dp = 16.dp

    /** 세그먼트 등 좁은 자리 */
    val sm: Dp = 18.dp

    /** 리스트·설정 행 기본 */
    val md: Dp = 20.dp

    /** 바텀 네비 */
    val lg: Dp = 24.dp

    /** FAB·평가 선택 카드 */
    val xl: Dp = 26.dp

    /** 빈 상태 일러스트 */
    val xxl: Dp = 40.dp
}

/**
 * 접근성 권장 최소 터치 타깃
 *
 * 시각 요소가 이보다 작으면 바깥 터치 영역만 이 크기로 넓힐 것
 */
val StashMinTouchTarget: Dp = 48.dp
