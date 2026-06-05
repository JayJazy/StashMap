package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * 현재 Stash 테마가 다크 모드인지 여부. [stashShadow] 등 비-Composable Modifier 가
 * 테마에 맞는 그림자 색을 고르도록 [StashTheme] 이 제공한다.
 */
internal val LocalStashIsDarkTheme = staticCompositionLocalOf { false }

/**
 * Stash Design System 테마.
 *
 * 라이트/다크에 맞는 [StashColors] 와 [StashTypography] 를 [LocalStashColors] / [LocalStashTypography]
 * 로 제공한 뒤, 기존 프로젝트와 동일하게 [MaterialTheme] 으로 감싼다.
 *
 * @param darkTheme 다크 테마 여부 (기본: 시스템 설정)
 * @param content 테마가 적용될 콘텐츠
 */
@Composable
fun StashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkStashColors else LightStashColors

    CompositionLocalProvider(
        LocalStashColors provides colors,
        LocalStashTypography provides stashTypography(),
        LocalStashIsDarkTheme provides darkTheme,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterialColorScheme(darkTheme),
            content = content,
        )
    }
}

/**
 * Stash 시맨틱 토큰을 Material3 [ColorScheme] 으로 매핑한다.
 *
 * Stash 컴포넌트는 자체 토큰을 쓰지만, 같은 트리에 섞여 쓰이는 순수 Material3 컴포넌트
 * (RadioButton / Checkbox / OutlinedTextField 등)도 테마(다크/라이트)를 따르도록
 * colorScheme 을 stash 토큰에서 파생해 주입한다.
 */
private fun StashColors.toMaterialColorScheme(darkTheme: Boolean): ColorScheme {
    val base = if (darkTheme) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = accent,
        onPrimary = accentFg,
        primaryContainer = accentSubtle,
        onPrimaryContainer = accentSubtleFg,
        secondary = accent,
        onSecondary = accentFg,
        background = bg,
        onBackground = fg,
        surface = surface,
        onSurface = fg,
        surfaceVariant = surface2,
        onSurfaceVariant = fgMuted,
        error = error,
        onError = errorFg,
        errorContainer = errorSubtle,
        onErrorContainer = errorSubtleFg,
        outline = border,
        outlineVariant = divider,
    )
}

/**
 * 현재 Stash 테마가 다크 모드인지 여부. core/ui 등 다른 모듈이 [LocalStashIsDarkTheme]
 * (internal) 대신 사용한다.
 */
val MaterialTheme.isStashDarkTheme: Boolean
    @Composable
    get() = LocalStashIsDarkTheme.current

/**
 * 현재 컴포지션의 Stash 색상 토큰에 접근하기 위한 확장 프로퍼티.
 *
 * 사용 예시:
 * ```kotlin
 * Text(color = MaterialTheme.stashColorTokens.fg, text = "Hello")
 * ```
 */
val MaterialTheme.stashColorTokens: StashColors
    @Composable
    get() = LocalStashColors.current

/**
 * 현재 컴포지션의 Stash 타이포그래피에 접근하기 위한 확장 프로퍼티.
 */
val MaterialTheme.stashTypography: StashTypography
    @Composable
    get() = LocalStashTypography.current
