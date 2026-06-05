package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Stash Design System 의 그림자 단계.
 */
enum class StashShadowLevel(val elevation: Dp) {
    Xs(1.dp),
    Sm(2.dp),
    Md(4.dp),
    Lg(12.dp),
    Sheet(16.dp),
}

/**
 * 그림자 색상 토큰. 라이트는 slate 계열 낮은 채도, 다크는 검정 기반으로 저대비를 유지한다.
 */
object StashElevation {
    /** 라이트 테마 ambient/spot 그림자 색상 (slate900 저알파). */
    val lightShadowColor: Color = Color(0xFF0F172A)

    /** 다크 테마 ambient/spot 그림자 색상 (검정). */
    val darkShadowColor: Color = Color(0xFF000000)
}

/**
 * Stash 그림자를 적용하는 [Modifier].
 *
 * 그림자 색은 현재 [StashTheme] 의 다크 여부([LocalStashIsDarkTheme])에 따라 자동 선택된다.
 *
 * @param level 그림자 단계
 * @param shape 그림자/클립 형태
 */
fun Modifier.stashShadow(
    level: StashShadowLevel,
    shape: Shape = RectangleShape,
): Modifier = composed {
    val dark = LocalStashIsDarkTheme.current
    val shadowColor = if (dark) StashElevation.darkShadowColor else StashElevation.lightShadowColor
    this.shadow(
        elevation = level.elevation,
        shape = shape,
        clip = false,
        ambientColor = shadowColor,
        spotColor = shadowColor,
    )
}
