package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * Stash Design System 의 시맨틱 색상 토큰.
 *
 * 원시 색상([StashPrimitives])을 역할(role) 기반 별칭으로 노출한다.
 * 라이트/다크 두 가지 인스턴스가 존재하며, [StashTheme] 를 통해 [LocalStashColors] 로 제공된다.
 *
 * 사용 예시:
 * ```kotlin
 * Text(color = MaterialTheme.stashColorTokens.fg, text = "Hello")
 * ```
 */
@Immutable
data class StashColors(
    // Surface / background
    val bg: Color,
    val surface: Color,
    val surface2: Color,
    val surface3: Color,
    val overlay: Color,
    // Foreground / text
    val fg: Color,
    val fgMuted: Color,
    val fgSubtle: Color,
    val fgOnAccent: Color,
    // Lines
    val border: Color,
    val borderStrong: Color,
    val divider: Color,
    // Accent
    val accent: Color,
    val accentHover: Color,
    val accentPress: Color,
    val accentFg: Color,
    val accentSubtle: Color,
    val accentSubtleFg: Color,
    val accentRing: Color,
    // Success
    val success: Color,
    val successFg: Color,
    val successSubtle: Color,
    val successSubtleFg: Color,
    // Warning
    val warning: Color,
    val warningFg: Color,
    val warningSubtle: Color,
    val warningSubtleFg: Color,
    // Error
    val error: Color,
    val errorFg: Color,
    val errorSubtle: Color,
    val errorSubtleFg: Color,
    // Info
    val info: Color,
    val infoFg: Color,
    val infoSubtle: Color,
    val infoSubtleFg: Color,
    // Field / chip
    val fieldBg: Color,
    val fieldBorder: Color,
    val chipBg: Color,
    val chipFg: Color,
    // Rating
    val gold400: Color,
    val gold500: Color,
)

/**
 * 라이트 테마 시맨틱 색상.
 */
val LightStashColors: StashColors = StashColors(
    bg = StashPrimitives.Slate50,
    surface = StashPrimitives.Slate0,
    surface2 = StashPrimitives.Slate100,
    surface3 = StashPrimitives.Slate200,
    overlay = Color(0xFF080F1F).copy(alpha = 0.45f),
    fg = StashPrimitives.Slate900,
    fgMuted = StashPrimitives.Slate500,
    fgSubtle = StashPrimitives.Slate400,
    fgOnAccent = StashPrimitives.Slate0,
    border = StashPrimitives.Slate200,
    borderStrong = StashPrimitives.Slate300,
    divider = StashPrimitives.Slate100,
    accent = StashPrimitives.Accent600,
    accentHover = StashPrimitives.Accent700,
    accentPress = StashPrimitives.Accent800,
    accentFg = StashPrimitives.Slate0,
    accentSubtle = StashPrimitives.Accent50,
    accentSubtleFg = StashPrimitives.Accent700,
    accentRing = StashPrimitives.Accent500.copy(alpha = 0.35f),
    success = StashPrimitives.Green600,
    successFg = StashPrimitives.Slate0,
    successSubtle = StashPrimitives.Green50,
    successSubtleFg = StashPrimitives.Green700,
    warning = StashPrimitives.Amber500,
    warningFg = StashPrimitives.Slate900,
    warningSubtle = StashPrimitives.Amber50,
    warningSubtleFg = StashPrimitives.Amber700,
    error = StashPrimitives.Red600,
    errorFg = StashPrimitives.Slate0,
    errorSubtle = StashPrimitives.Red50,
    errorSubtleFg = StashPrimitives.Red700,
    info = StashPrimitives.Blue600,
    infoFg = StashPrimitives.Slate0,
    infoSubtle = StashPrimitives.Blue50,
    infoSubtleFg = StashPrimitives.Blue700,
    fieldBg = StashPrimitives.Slate0,
    fieldBorder = StashPrimitives.Slate300,
    chipBg = StashPrimitives.Slate100,
    chipFg = StashPrimitives.Slate700,
    gold400 = StashPrimitives.Gold400,
    gold500 = StashPrimitives.Gold500,
)

/**
 * 다크 테마 시맨틱 색상.
 *
 * CSS 의 `color-mix(in srgb, A p%, B)` 는 `A` 를 `p%`, `B` 를 `(1-p)%` 섞는 것이므로
 * Compose 의 [lerp]`(start = B, stop = A, fraction = p/100)` 로 환산해 사전 계산한다.
 */
val DarkStashColors: StashColors = StashColors(
    bg = StashPrimitives.Slate950,
    surface = StashPrimitives.Slate900,
    surface2 = StashPrimitives.Slate800,
    surface3 = StashPrimitives.Slate700,
    overlay = Color(0xFF020610).copy(alpha = 0.62f),
    fg = StashPrimitives.Slate50,
    fgMuted = StashPrimitives.Slate400,
    fgSubtle = StashPrimitives.Slate500,
    fgOnAccent = StashPrimitives.Slate0,
    border = StashPrimitives.Slate800,
    borderStrong = StashPrimitives.Slate700,
    divider = StashPrimitives.Slate800,
    accent = StashPrimitives.Accent500,
    accentHover = StashPrimitives.Accent400,
    accentPress = StashPrimitives.Accent300,
    accentFg = StashPrimitives.Slate950,
    accentSubtle = lerp(StashPrimitives.Slate900, StashPrimitives.Accent500, 0.16f),
    accentSubtleFg = StashPrimitives.Accent200,
    accentRing = StashPrimitives.Accent400.copy(alpha = 0.45f),
    success = StashPrimitives.Green500,
    successFg = StashPrimitives.Slate950,
    successSubtle = lerp(StashPrimitives.Slate900, StashPrimitives.Green500, 0.16f),
    successSubtleFg = StashPrimitives.Green300,
    warning = StashPrimitives.Amber500,
    warningFg = StashPrimitives.Slate950,
    warningSubtle = lerp(StashPrimitives.Slate900, StashPrimitives.Amber500, 0.16f),
    warningSubtleFg = StashPrimitives.Amber300,
    error = StashPrimitives.Red500,
    errorFg = StashPrimitives.Slate950,
    errorSubtle = lerp(StashPrimitives.Slate900, StashPrimitives.Red500, 0.18f),
    errorSubtleFg = StashPrimitives.Red300,
    info = StashPrimitives.Blue500,
    infoFg = StashPrimitives.Slate950,
    infoSubtle = lerp(StashPrimitives.Slate900, StashPrimitives.Blue500, 0.18f),
    infoSubtleFg = StashPrimitives.Blue300,
    fieldBg = StashPrimitives.Slate900,
    fieldBorder = StashPrimitives.Slate700,
    chipBg = StashPrimitives.Slate800,
    chipFg = StashPrimitives.Slate200,
    gold400 = StashPrimitives.Gold400,
    gold500 = StashPrimitives.Gold500,
)

/**
 * 현재 컴포지션의 Stash 색상 토큰. 기본값은 [LightStashColors].
 */
val LocalStashColors = staticCompositionLocalOf { LightStashColors }
