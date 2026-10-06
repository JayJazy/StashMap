package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * Stash Design System 의 시맨틱 색상 토큰
 *
 * - 원시 색상([StashPrimitives])을 역할(role) 기반 별칭으로 노출
 * - 라이트/다크 두 인스턴스, [StashTheme] 가 [LocalStashColors] 로 제공
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
    // 아래 셋은 중립 면(surface/bg) 기준값 — 색조 면(`*Subtle`) 위엔 짝 토큰 `*SubtleFg` 를 쓸 것.
    // 그대로 올리면 라이트 fgMuted on Accent50 = 4.34 로 1.4.3(4.5:1) 미달
    val fg: Color,
    val fgMuted: Color,
    // 라이트 최대 2.56(흰 면 위) — 텍스트도 아이콘도 불가. 의미 있는 전경은 fgMuted 가 하한, 여긴 장식만
    val fgSubtle: Color,
    // accent 면 전용 — 위 규칙과 무관
    val fgOnAccent: Color,
    // Lines — 1.20~1.48 이라 3:1 을 못 넘는다. 컨테이너 외곽·구분선, 그리고 내용물(썸네일·아이콘·라벨)이
    //  스스로 식별되는 컨트롤 외곽까지는 1.4.11 비대상이라 괜찮다 (StashCard·StashEvalStatGrid 미선택 셀 등).
    //  내용물만으로 컨트롤임을 알 수 없는 자리에는 쓰지 말 것 — 그 자리는 fieldBorder
    val border: Color,
    val borderStrong: Color,
    val divider: Color,
    // Accent — 전경으로 쓸 수 있는 자리가 좁다. 다크 기준 accentSubtle 위 3.59 / surface 위 4.22 로 둘 다
    //  1.4.3(4.5:1) 미달이라 본문·라벨에는 accentSubtleFg 를 쓴다.
    //  중립 면(bg/surface) 위 큰 텍스트는 예외 — 1.4.11(3:1) 기준이라 통과한다 (Home 워드마크·카운트 강조)
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
    // Warning — 라이트 Amber600 은 면에 따라 갈린다. surface 3.19 / bg 3.04 까지만 아이콘 가능(StatCard),
    //  surface2 이하는 2.91 이라 1.4.11(3:1) 미달. 텍스트는 어느 면에서도 불가 — warningSubtleFg 를 쓴다
    val warning: Color,
    // 라이트는 Slate900 유지 — 다른 600 토큰처럼 Slate0 로 맞추면 3.19 라 1.4.3(4.5:1) 미달
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
    // 컨트롤 외곽 — 흰 필드가 흰 바탕 위라(면차 1.05) 테두리가 유일한 식별 수단이다. 1.4.11(3:1) 대상.
    // 라이트·다크 모두 Slate500 고정. 다크 fieldBg 위 기준 1.72 → 3.75, 전 면 최저는 3.07(다크 surface2).
    // Slate400 은 라이트 2.56 이라 못 쓰고 램프의 다음 단계가 바로 Slate500 이다
    val fieldBorder: Color,
    val chipBg: Color,
    val chipFg: Color,
)

/** 라이트 테마 시맨틱 색상 */
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
    warning = StashPrimitives.Amber600,
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
    fieldBorder = StashPrimitives.Slate500,
    chipBg = StashPrimitives.Slate100,
    chipFg = StashPrimitives.Slate700,
)

/**
 * 다크 테마 시맨틱 색상
 *
 * CSS `color-mix(in srgb, A p%, B)` = A 를 p%, B 를 (1-p)%
 * → Compose [lerp]`(start = B, stop = A, fraction = p/100)` 로 환산해 사전 계산
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
    fieldBorder = StashPrimitives.Slate500,
    chipBg = StashPrimitives.Slate800,
    chipFg = StashPrimitives.Slate200,
)

/** 현재 컴포지션의 Stash 색상 토큰 — 기본값 [LightStashColors] */
val LocalStashColors = staticCompositionLocalOf { LightStashColors }
