package com.jayys.stashmap.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.R

@Composable
fun HDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = colorResource(id = R.color.gray2)
) {
    HorizontalDivider(
        thickness = thickness,
        color = color,
        modifier = modifier
            .fillMaxWidth()
    )
}