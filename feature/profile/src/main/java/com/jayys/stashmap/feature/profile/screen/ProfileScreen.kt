package com.jayys.stashmap.feature.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jayys.stashmap.compose.SpacerHeight
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography
import com.jayys.stashmap.core.model.StashMapLanguage
import com.jayys.stashmap.feature.profile.ui.ActivityStatCards
import com.jayys.stashmap.feature.profile.ui.PreferenceItem
import com.jayys.stashmap.feature.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
    onInformationClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    ProfileContent(
        modifier = modifier,
        selectedLanguage = selectedLanguage,
        onLanguageClick = onLanguageClick,
        onThemeClick = onThemeClick,
        onInformationClick = onInformationClick,
        onContactClick = onContactClick
    )
}

@Composable
private fun ProfileContent(
    selectedLanguage: StashMapLanguage,
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
    onInformationClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.stashColorTokens.bg)
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.activity_stats),
            color = MaterialTheme.stashColorTokens.fg,
            style = MaterialTheme.stashTypography.h3,
            modifier = Modifier.padding(start = 4.dp)
        )

        SpacerHeight(12.dp)

        ActivityStatCards(
            topCategory = "French",
            addedThisMonth = "99",
        )

        SpacerHeight(36.dp)

        Text(
            text = stringResource(id = R.string.preferences),
            color = MaterialTheme.stashColorTokens.fg,
            style = MaterialTheme.stashTypography.h3,
            modifier = Modifier.padding(start = 4.dp)
        )

        SpacerHeight(12.dp)

        PreferenceItem(
            selectedLanguage = selectedLanguage,
            onLanguageClick = onLanguageClick,
            onThemeClick = onThemeClick,
            onInformationClick = onInformationClick,
            onContactClick = onContactClick
        )

        SpacerHeight(20.dp)

        Text(
            text = stringResource(id = R.string.version, "1.0.0"),
            style = MaterialTheme.stashTypography.caption.copy(
                color = MaterialTheme.stashColorTokens.fgMuted
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileContent() {
    ProfileContent(
        selectedLanguage = StashMapLanguage.KOREAN,
        onLanguageClick = {},
        onThemeClick = {},
        onInformationClick = {},
        onContactClick = {}
    )
}