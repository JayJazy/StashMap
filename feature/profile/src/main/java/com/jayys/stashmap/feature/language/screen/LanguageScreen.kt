package com.jayys.stashmap.feature.language.screen

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.stashmap.component.SMTopBar
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.theme.stashColors
import com.jayys.stashmap.core.model.StashMapLanguage
import com.jayys.stashmap.feature.language.model.LanguageUiState
import com.jayys.stashmap.feature.language.ui.LanguageSearchBar
import com.jayys.stashmap.feature.language.ui.LanguageSelectionItem
import com.jayys.stashmap.feature.language.viewmodel.LanguageViewModel

@Composable
fun LanguageScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LanguageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LanguageContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onLanguageSelect = viewModel::selectLanguage,
        onBack = onBack,
        modifier = modifier
    )

}

@Composable
fun LanguageContent(
    uiState: LanguageUiState,
    onSearchQueryChange: (String) -> Unit,
    onLanguageSelect: (StashMapLanguage) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SMTopBar(
                topBarTitle = stringResource(id = R.string.language),
                onClick = onBack,
            )
        },
        containerColor = MaterialTheme.stashColors.bgColor
    ) { paddingValues ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 60.dp
                )
        ) {
            LanguageSearchBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            LanguageSelectionItem(
                languages = uiState.availableLanguages,
                selectedLanguage = uiState.selectedLanguage,
                onLanguageSelect = { language ->
                    if (language != uiState.selectedLanguage) {
                        onLanguageSelect(language)
                        (context as? Activity)?.recreate()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLanguageContent() {
    LanguageContent(
        uiState = LanguageUiState(
            searchQuery = "",
            selectedLanguage = StashMapLanguage.KOREAN,
            availableLanguages = StashMapLanguage.entries
        ),
        onBack = {},
        onSearchQueryChange = {},
        onLanguageSelect = {},
    )
}