package com.jayys.stashmap.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.compose.SpacerWidth
import com.jayys.stashmap.compose.clickableNoRipple
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.stashColors
import com.jayys.stashmap.core.designsystem.theme.typography

@Composable
fun SMTopBar(
    topBarTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.stashColors.bgColor)
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ico_arrow_back),
            contentDescription = "",
            tint = MaterialTheme.stashColors.baseColor,
            modifier = Modifier.clickableNoRipple { onClick() }
        )

        SpacerWidth(12.dp)

        Text(
            text = topBarTitle,
            style = typography(TextStyleEnum.Title2)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSMTopBar() {
    SMTopBar(
        topBarTitle = "profile",
        onClick = {}
    )
}