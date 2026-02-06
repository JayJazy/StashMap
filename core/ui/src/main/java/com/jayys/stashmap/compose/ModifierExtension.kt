package com.jayys.stashmap.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerHeight(dp: Dp) = Spacer(Modifier.height(dp))

@Composable
fun SpacerWidth(dp: Dp) = Spacer(Modifier.width(dp))

@Composable
inline fun Modifier.clickableNoRipple(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit
): Modifier = this.composed {
    clickable(
        enabled = enabled,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}