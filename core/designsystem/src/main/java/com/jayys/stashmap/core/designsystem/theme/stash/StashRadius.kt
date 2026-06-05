package com.jayys.stashmap.core.designsystem.theme.stash

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Stash Design System 의 코너 반경(radius) 토큰.
 *
 * 각 토큰은 [Dp] 원시값(`*Dp`)과 바로 쓸 수 있는 [RoundedCornerShape] 를 모두 제공한다.
 * `full` 은 완전한 알약/원형 형태를 위해 [RoundedCornerShape]`(percent = 50)` 으로 노출한다.
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
