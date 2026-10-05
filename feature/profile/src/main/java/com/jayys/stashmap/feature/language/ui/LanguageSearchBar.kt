package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.feature.profile.R

@Composable
fun LanguageSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_language),
                color = MaterialTheme.stashColorTokens.fgMuted,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = StashIcons.Search,
                // placeholder 가 같은 의미를 전달하므로 장식
                contentDescription = null,
                tint = MaterialTheme.stashColorTokens.fgSubtle
            )
        },
        modifier = modifier
            .background(
                color = MaterialTheme.stashColorTokens.fieldBg,
                shape = StashRadius.md
            )
            .fillMaxWidth(),
        shape = StashRadius.md,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = MaterialTheme.stashColorTokens.fg,
            unfocusedTextColor = MaterialTheme.stashColorTokens.fg
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewLanguageSearchBar() {
    LanguageSearchBar(
        searchQuery = "",
        onSearchQueryChange = {}
    )
}