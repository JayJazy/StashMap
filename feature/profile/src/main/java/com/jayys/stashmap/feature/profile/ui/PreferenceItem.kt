package com.jayys.stashmap.feature.profile.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.component.stash.StashCard
import com.jayys.stashmap.core.designsystem.component.stash.StashDivider
import com.jayys.stashmap.core.designsystem.component.stash.StashSettingRow
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.model.StashMapLanguage
import com.jayys.stashmap.feature.profile.R

/**
 * 프로필의 설정 묶음 — 언어 / 테마 / 정보 / 문의
 *
 * 행 자체가 좌우 여백을 가지므로 카드는 위아래 여백만 줌
 */
@Composable
fun PreferenceItem(
    selectedLanguage: StashMapLanguage,
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
    onInformationClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    StashCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = StashSpacing.s2)
    ) {
        StashSettingRow(
            label = stringResource(id = R.string.language),
            icon = StashIcons.Languages,
            value = selectedLanguage.displayName,
            onClick = onLanguageClick
        )

        StashDivider()

        StashSettingRow(
            label = stringResource(id = R.string.system_theme),
            icon = StashIcons.Moon,
            onClick = onThemeClick
        )

        StashDivider()

        StashSettingRow(
            label = stringResource(id = R.string.information),
            icon = StashIcons.Info,
            onClick = onInformationClick
        )

        StashDivider()

        StashSettingRow(
            label = stringResource(id = R.string.contact),
            icon = StashIcons.HelpCircle,
            onClick = onContactClick
        )
    }
}

@Composable
private fun PreferenceItemSample() {
    PreferenceItem(
        selectedLanguage = StashMapLanguage.KOREAN,
        onLanguageClick = {},
        onThemeClick = {},
        onInformationClick = {},
        onContactClick = {}
    )
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun PreferenceItemLightPreview() {
    StashTheme(darkTheme = false) {
        PreferenceItemSample()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun PreferenceItemDarkPreview() {
    StashTheme(darkTheme = true) {
        PreferenceItemSample()
    }
}
