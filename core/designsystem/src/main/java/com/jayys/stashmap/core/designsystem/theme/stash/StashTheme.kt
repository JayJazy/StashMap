package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/** [stashShadow] 처럼 비-Composable Modifier 가 테마에 맞는 색을 고르는 데 필요 */
internal val LocalStashIsDarkTheme = staticCompositionLocalOf { false }

/**
 * Stash Design System 테마
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

/** 같은 트리의 Material3 컴포넌트(RadioButton 등)도 테마를 따르게 하려고 파생 */
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

/** 다른 모듈이 internal 인 [LocalStashIsDarkTheme] 대신 사용 */
val MaterialTheme.isStashDarkTheme: Boolean
    @Composable
    get() = LocalStashIsDarkTheme.current

/**
 * 현재 컴포지션의 Stash 색상 토큰 접근용 확장 프로퍼티
 *
 * 사용 예시:
 * ```kotlin
 * Text(color = MaterialTheme.stashColorTokens.fg, text = "Hello")
 * ```
 */
val MaterialTheme.stashColorTokens: StashColors
    @Composable
    get() = LocalStashColors.current

/** 현재 컴포지션의 Stash 타이포그래피 접근용 확장 프로퍼티 */
val MaterialTheme.stashTypography: StashTypography
    @Composable
    get() = LocalStashTypography.current
