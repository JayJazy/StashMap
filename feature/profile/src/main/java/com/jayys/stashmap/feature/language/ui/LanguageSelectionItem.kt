package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.component.HDivider
import com.jayys.stashmap.component.SMSettingItem
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.stashColors

@Composable
fun LanguageSelectionItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.stashColors.grayLight2,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = MaterialTheme.stashColors.bgColor,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        List(20) { "language " }.forEachIndexed { index, item ->
            SMSettingItem(
                title = "$item $index",
                icon = painterResource(id = R.drawable.ico_globe),
                iconTint = MaterialTheme.stashColors.greenDark1,
                onClick = { },
                trailing = {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { },
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            HDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLanguageSelectionItem() {
    LanguageSelectionItem()
}