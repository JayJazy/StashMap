package com.jayys.stashmap.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.component.HDivider
import com.jayys.stashmap.component.SMSettingItem
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.typography

@Composable
fun PreferenceItem(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
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
                color = colorResource(id = R.color.gray2),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = colorResource(id = R.color.bg_color),
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        SMSettingItem(
            title = stringResource(id = R.string.language),
            icon = painterResource(id = R.drawable.ico_globe),
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
                            colorResource(id = R.color.gray3)
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ico_arrow_right),
                        contentDescription = "",
                        tint = colorResource(id = R.color.gray2)
                    )
                }
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.system_theme),
            icon = painterResource(id = R.drawable.ico_moon),
            onClick = onThemeClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = colorResource(id = R.color.gray2)
                )
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.informateion),
            icon = painterResource(id = R.drawable.ico_information),
            onClick = onInformationClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = colorResource(id = R.color.gray2)
                )
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.contact),
            icon = painterResource(id = R.drawable.ico_help),
            onClick = onContactClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = colorResource(id = R.color.gray2)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPreferenceItem() {
    PreferenceItem(
        isDarkMode = false,
        onDarkModeChange = {},
        onLanguageClick = {},
        onThemeClick = {},
        onInformationClick = {},
        onContactClick = {}
    )
}