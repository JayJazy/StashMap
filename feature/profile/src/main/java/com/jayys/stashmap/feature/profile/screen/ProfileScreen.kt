package com.jayys.stashmap.feature.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.compose.SpacerHeight
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.typography
import com.jayys.stashmap.feature.profile.ui.ActivityStatCards
import com.jayys.stashmap.feature.profile.ui.PreferenceItem

@Composable
fun ProfileScreen(
    isDarkMode: MutableState<Boolean>,
    onDarkModeChange: (Boolean) -> Unit,
    onLanguageClick: () -> Unit,
    onThemeClick: () -> Unit,
    onInformationClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ProfileContent(
        modifier = modifier,
        isDarkMode = isDarkMode.value,
        onDarkModeChange = onDarkModeChange,
        onLanguageClick = onLanguageClick,
        onThemeClick = onThemeClick,
        onInformationClick = onInformationClick,
        onContactClick = onContactClick
    )
}

@Composable
private fun ProfileContent(
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
            .background(color = colorResource(id = R.color.bg_color))
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.activity_stats),
            style = typography(TextStyleEnum.Title2),
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
            style = typography(TextStyleEnum.Title2),
            modifier = Modifier.padding(start = 4.dp)
        )

        SpacerHeight(12.dp)

        PreferenceItem(
            isDarkMode = isDarkMode,
            onDarkModeChange = onDarkModeChange,
            onLanguageClick = onLanguageClick,
            onThemeClick = onThemeClick,
            onInformationClick = onInformationClick,
            onContactClick = onContactClick
        )

        SpacerHeight(20.dp)

        Text(
            text = stringResource(id = R.string.version, "1.0.0"),
            style = typography(TextStyleEnum.OverLine).copy(
                color = colorResource(id = R.color.gray3)
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileContent() {
    ProfileContent(
        isDarkMode = false,
        onDarkModeChange = {},
        onLanguageClick = {},
        onThemeClick = {},
        onInformationClick = {},
        onContactClick = {}
    )
}