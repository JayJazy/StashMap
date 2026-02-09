package com.jayys.stashmap.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

/**
 * 다크 모드 상태를 전역으로 관리하는 CompositionLocal
 */
val LocalIsDarkMode = compositionLocalOf { false }

/**
 * StashMap 커스텀 색상에 접근하기 위한 확장 프로퍼티
 *
 * 사용 예시:
 * ```kotlin
 * Text(
 *     text = "Hello",
 *     color = MaterialTheme.stashColors.purple5
 * )
 * ```
 */
val MaterialTheme.stashColors: StashMapColors
    @Composable
    get() = LocalStashMapColors.current

@Composable
fun StashMapTheme(
    isDarkMode: Boolean,
    content: @Composable () -> Unit
) {
    val customColors = if (isDarkMode) DarkColors else LightColors

    CompositionLocalProvider(
        LocalIsDarkMode provides (isDarkMode),
        LocalStashMapColors provides customColors
    ) {
        MaterialTheme(content = content)
    }
}