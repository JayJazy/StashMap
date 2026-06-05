package com.jayys.stashmap.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography

@Composable
fun SMSettingItem(
    title: String,
    icon: Painter,
    iconTint: Color,
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
        Icon(
            painter = icon,
            contentDescription = "Preference Icon",
            tint = iconTint,
            modifier = Modifier.size(30.dp)
        )

        Text(
            text = title,
            color = MaterialTheme.stashColorTokens.fg,
            style = MaterialTheme.stashTypography.caption,
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
        iconTint = MaterialTheme.stashColorTokens.warning,
        trailing = {}
    )
}