package com.jayys.stashmap.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.compose.clickableNoRipple
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.typography

@Composable
fun SMSettingItem(
    title: String,
    icon: Painter,
    trailing: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickableNoRipple { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.blue2),
                    shape = CircleShape
                )
                .size(30.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = "",
                tint = colorResource(id = R.color.green3),
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = title,
            style = typography(TextStyleEnum.Body3),
            modifier = Modifier.weight(1f)
        )

        trailing()
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewSMSettingItem() {
    SMSettingItem(
        title = "contact",
        icon = painterResource(id = R.drawable.ico_help),
        trailing = {}
    )
}