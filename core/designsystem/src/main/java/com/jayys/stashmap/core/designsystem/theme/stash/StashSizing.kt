package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Stash Design System 의 아이콘 크기 토큰
 *
 * 간격([StashSpacing])과 달리 4dp 그리드를 따르지 않음 — 아이콘은 광학 크기로 고름
 *
 * 드로어블이 24dp 뷰포트에 2dp 외곽선이라 **실효 두께가 크기에 비례**한다.
 * 크게 쓸수록 선이 굵어 보이므로, 32dp 를 넘겨 쓰려면 전용 리소스를 따로 두는 편이 낫다
 */
object StashIconSize {
    /** 작은 버튼·칩 안 — 실효 선 1.33dp */
    val xs: Dp = 16.dp

    /** 세그먼트 등 좁은 자리 — 1.5dp */
    val sm: Dp = 18.dp

    /** 리스트·설정 행 기본 — 1.67dp */
    val md: Dp = 20.dp

    /** 바텀 네비 — 2dp (드로어블 기준 크기) */
    val lg: Dp = 24.dp

    /** FAB·평가 선택 카드 — 2.17dp */
    val xl: Dp = 26.dp

    /** 빈 상태 일러스트 — 2.67dp. 이보다 키우면 선이 눈에 띄게 굵어진다 */
    val xxl: Dp = 32.dp
}

/**
 * 접근성 권장 최소 터치 타깃
 *
 * 시각 요소가 이보다 작으면 바깥 터치 영역만 이 크기로 넓힐 것
 */
val StashMinTouchTarget: Dp = 48.dp
