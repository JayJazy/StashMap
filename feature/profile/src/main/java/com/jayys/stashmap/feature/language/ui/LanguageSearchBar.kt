package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.component.stash.StashTextField
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.feature.profile.R

@Composable
fun LanguageSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    StashTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = stringResource(id = R.string.search_language),
        leadingIcon = StashIcons.Search
    )
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun LanguageSearchBarLightPreview() {
    StashTheme(darkTheme = false) {
        LanguageSearchBar(searchQuery = "", onSearchQueryChange = {})
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun LanguageSearchBarDarkPreview() {
    StashTheme(darkTheme = true) {
        LanguageSearchBar(searchQuery = "", onSearchQueryChange = {})
    }
}
