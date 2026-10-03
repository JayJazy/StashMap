package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Stash Design System 의 코너 반경(radius) 토큰
 *
 * - 토큰마다 [Dp] 원시값(`*Dp`) + [RoundedCornerShape] 둘 다 제공
 * - `full` 은 알약/원형용으로 [RoundedCornerShape]`(percent = 50)`
 */
object StashRadius {
    val xsDp: Dp = 6.dp
    val smDp: Dp = 8.dp
    val mdDp: Dp = 12.dp
    val lgDp: Dp = 16.dp
    val xlDp: Dp = 20.dp
    val xxlDp: Dp = 28.dp
    val fullDp: Dp = 999.dp

    val xs: RoundedCornerShape = RoundedCornerShape(xsDp)
    val sm: RoundedCornerShape = RoundedCornerShape(smDp)
    val md: RoundedCornerShape = RoundedCornerShape(mdDp)
    val lg: RoundedCornerShape = RoundedCornerShape(lgDp)
    val xl: RoundedCornerShape = RoundedCornerShape(xlDp)
    val xxl: RoundedCornerShape = RoundedCornerShape(xxlDp)
    val full: RoundedCornerShape = RoundedCornerShape(percent = 50)
}
