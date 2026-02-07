package com.jayys.stashmap.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.component.HDivider
import com.jayys.stashmap.component.SMSettingItem
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.stashColors
import com.jayys.stashmap.core.designsystem.theme.typography

@Composable
fun PreferenceItem(
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
    onInformationClick: () -> Unit,
    onContactClick: () -> Unit,
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
        SMSettingItem(
            title = stringResource(id = R.string.language),
            icon = painterResource(id = R.drawable.ico_globe),
            iconTint = MaterialTheme.stashColors.greenDark1,
            onClick = onLanguageClick,
            trailing = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "English(US)",
                        style = typography(TextStyleEnum.Body3).copy(
                            color = MaterialTheme.stashColors.grayLight2
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ico_arrow_right),
                        contentDescription = "",
                        tint = MaterialTheme.stashColors.grayDark1
                    )
                }
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.system_theme),
            icon = painterResource(id = R.drawable.ico_moon),
            iconTint = MaterialTheme.stashColors.yellowLight2,
            onClick = onThemeClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = MaterialTheme.stashColors.grayDark1
                )
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.informateion),
            icon = painterResource(id = R.drawable.ico_information),
            iconTint = MaterialTheme.stashColors.grayDark1,
            onClick = onInformationClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = MaterialTheme.stashColors.grayDark1
                )
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.contact),
            icon = painterResource(id = R.drawable.ico_help),
            iconTint = MaterialTheme.stashColors.grayDark1,
            onClick = onContactClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = MaterialTheme.stashColors.grayDark1
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPreferenceItem() {
    PreferenceItem(
        onLanguageClick = {},
        onThemeClick = {},
        onInformationClick = {},
        onContactClick = {}
    )
}