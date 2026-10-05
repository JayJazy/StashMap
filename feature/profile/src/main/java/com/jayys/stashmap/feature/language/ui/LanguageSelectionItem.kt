package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.component.legacy.HDivider
import com.jayys.stashmap.core.designsystem.component.stash.StashCard
import com.jayys.stashmap.core.model.StashMapLanguage

@Composable
fun LanguageSelectionItem(
    languages: List<StashMapLanguage>,
    selectedLanguage: StashMapLanguage,
    onLanguageSelect: (StashMapLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    StashCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp)
    ) {
        languages.forEachIndexed { index, language ->
            val isSelected = selectedLanguage == language

            LanguageItem(
                language = language,
                isSelected = isSelected,
                onLanguageSelect = onLanguageSelect
            )

            if (index < languages.size - 1) {
                HDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLanguageSelectionItem() {
    LanguageSelectionItem(
        languages = StashMapLanguage.entries,
        selectedLanguage = StashMapLanguage.KOREAN,
        onLanguageSelect = {}
    )
}