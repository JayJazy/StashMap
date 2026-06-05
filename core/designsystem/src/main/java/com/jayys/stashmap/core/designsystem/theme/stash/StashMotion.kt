package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing

/**
 * Stash Design System 의 모션(motion) 토큰. 이징 곡선과 지속시간(ms)을 정의한다.
 */
object StashMotion {
    /** 표준 진입/진출 이징. */
    val easeStandard: Easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)

    /** 강조용 이징(오버슈트). */
    val easeEmphasis: Easing = CubicBezierEasing(0.2f, 0f, 0f, 1.2f)

    /** 빠른 전환 (ms). */
    const val durFast: Int = 140

    /** 기본 전환 (ms). */
    const val durBase: Int = 220

    /** 느린 전환 (ms). */
    const val durSlow: Int = 320
}
