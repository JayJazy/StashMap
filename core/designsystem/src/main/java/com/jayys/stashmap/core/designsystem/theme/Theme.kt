package com.jayys.stashmap.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

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
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 커스텀 색상 팔레트 선택
    val customColors = if (darkTheme) DarkColors else LightColors

    CompositionLocalProvider(LocalStashMapColors provides customColors) {
        MaterialTheme(content = content)
    }
}