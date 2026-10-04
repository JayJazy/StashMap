package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Stash Design System 의 텍스트 역할(role)
 *
 * - 역할마다 고정된 타이포그래피 메트릭 보유
 * - 기본 색상 토큰은 [defaultUsesMuted] 로 결정 (true → fgMuted, false → fg)
 * - 색상은 TextStyle 에 굽지 않고 `StashText` 에서 테마 토큰 참조로 해석
 */
enum class StashTextRole(internal val defaultUsesMuted: Boolean) {
    Display(false),
    H1(false),
    H2(false),
    H3(false),
    BodyLg(false),
    Body(false),
    BodySm(true),
    Label(false),
    Caption(true),
    Overline(true),
    Mono(false),
}

/** 역할별 [TextStyle] 묶음 — 색상은 [Color.Unspecified] 로 두고 `StashText` 에서 해석 */
@Immutable
data class StashTypography(
    val display: TextStyle,
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val bodyLg: TextStyle,
    val body: TextStyle,
    val bodySm: TextStyle,
    val label: TextStyle,
    val caption: TextStyle,
    val overline: TextStyle,
    val mono: TextStyle,
) {
    /** [role] 에 해당하는 [TextStyle] 반환 */
    operator fun get(role: StashTextRole): TextStyle = when (role) {
        StashTextRole.Display -> display
        StashTextRole.H1 -> h1
        StashTextRole.H2 -> h2
        StashTextRole.H3 -> h3
        StashTextRole.BodyLg -> bodyLg
        StashTextRole.Body -> body
        StashTextRole.BodySm -> bodySm
        StashTextRole.Label -> label
        StashTextRole.Caption -> caption
        StashTextRole.Overline -> overline
        StashTextRole.Mono -> mono
    }
}

/**
 * 픽셀 사이즈 + line-height 배수로 [TextStyle] 생성
 *
 * @param sizeSp 폰트 크기(sp 로 환산할 px 값)
 * @param weight 폰트 굵기 (Normal=400, Bold=700 만 — faux-bold 금지)
 * @param lineHeightMultiplier line-height 배수 (lineHeight = size * multiplier)
 * @param trackingEm 자간(em)
 * @param fontFamily 폰트 패밀리 (기본 Pretendard, Mono 는 Monospace)
 */
private fun stashStyle(
    sizeSp: Int,
    weight: FontWeight,
    lineHeightMultiplier: Float,
    trackingEm: Float,
    fontFamily: FontFamily = StashPretendard,
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = weight,
    fontSize = sizeSp.sp,
    lineHeight = (sizeSp * lineHeightMultiplier).sp,
    letterSpacing = trackingEm.em,
    color = Color.Unspecified,
)

/**
 * 기본 Stash 타이포그래피 생성
 *
 * 사이즈 px→sp / weight Normal·Bold 만 / line-height 배수 / tracking em
 */
fun stashTypography(): StashTypography = StashTypography(
    display = stashStyle(sizeSp = 30, weight = FontWeight.Bold, lineHeightMultiplier = 1.2f, trackingEm = -0.02f),
    h1 = stashStyle(sizeSp = 24, weight = FontWeight.Bold, lineHeightMultiplier = 1.2f, trackingEm = -0.02f),
    h2 = stashStyle(sizeSp = 20, weight = FontWeight.Bold, lineHeightMultiplier = 1.35f, trackingEm = 0f),
    h3 = stashStyle(sizeSp = 18, weight = FontWeight.Bold, lineHeightMultiplier = 1.35f, trackingEm = 0f),
    bodyLg = stashStyle(sizeSp = 18, weight = FontWeight.Normal, lineHeightMultiplier = 1.65f, trackingEm = 0f),
    body = stashStyle(sizeSp = 16, weight = FontWeight.Normal, lineHeightMultiplier = 1.5f, trackingEm = 0f),
    bodySm = stashStyle(sizeSp = 14, weight = FontWeight.Normal, lineHeightMultiplier = 1.5f, trackingEm = 0f),
    label = stashStyle(sizeSp = 14, weight = FontWeight.Normal, lineHeightMultiplier = 1.35f, trackingEm = 0f),
    caption = stashStyle(sizeSp = 12, weight = FontWeight.Normal, lineHeightMultiplier = 1.35f, trackingEm = 0.02f),
    overline = stashStyle(sizeSp = 12, weight = FontWeight.Bold, lineHeightMultiplier = 1.35f, trackingEm = 0.08f),
    mono = stashStyle(
        sizeSp = 14,
        weight = FontWeight.Normal,
        lineHeightMultiplier = 1.5f,
        trackingEm = 0f,
        fontFamily = FontFamily.Monospace,
    ),
)

/** 현재 컴포지션의 Stash 타이포그래피 — 기본값 [stashTypography] */
val LocalStashTypography = staticCompositionLocalOf { stashTypography() }
