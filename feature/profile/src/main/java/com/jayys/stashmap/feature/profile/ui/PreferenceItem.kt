package com.jayys.stashmap.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography
import com.jayys.stashmap.core.model.StashMapLanguage

@Composable
fun PreferenceItem(
    selectedLanguage: StashMapLanguage,
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
                color = MaterialTheme.stashColorTokens.border,
                shape = StashRadius.lg
            )
            .background(
                color = MaterialTheme.stashColorTokens.surface,
                shape = StashRadius.lg
            )
            .fillMaxWidth()
    ) {
        SMSettingItem(
            title = stringResource(id = R.string.language),
            icon = painterResource(id = R.drawable.ico_globe),
            iconTint = MaterialTheme.stashColorTokens.success,
            onClick = onLanguageClick,
            trailing = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = selectedLanguage.displayName,
                        style = MaterialTheme.stashTypography.caption.copy(
                            color = MaterialTheme.stashColorTokens.fgMuted
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ico_arrow_right),
                        contentDescription = "",
                        tint = MaterialTheme.stashColorTokens.fgMuted
                    )
                }
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.system_theme),
            icon = painterResource(id = R.drawable.ico_moon),
            iconTint = MaterialTheme.stashColorTokens.warning,
            onClick = onThemeClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = MaterialTheme.stashColorTokens.fgMuted
                )
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.informateion),
            icon = painterResource(id = R.drawable.ico_information),
            iconTint = MaterialTheme.stashColorTokens.fgMuted,
            onClick = onInformationClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = MaterialTheme.stashColorTokens.fgMuted
                )
            }
        )

        HDivider()

        SMSettingItem(
            title = stringResource(id = R.string.contact),
            icon = painterResource(id = R.drawable.ico_help),
            iconTint = MaterialTheme.stashColorTokens.fgMuted,
            onClick = onContactClick,
            trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.ico_arrow_right),
                    contentDescription = "",
                    tint = MaterialTheme.stashColorTokens.fgMuted
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPreferenceItem() {
    PreferenceItem(
        selectedLanguage = StashMapLanguage.KOREAN,
        onLanguageClick = {},
        onThemeClick = {},
        onInformationClick = {},
        onContactClick = {}
    )
}