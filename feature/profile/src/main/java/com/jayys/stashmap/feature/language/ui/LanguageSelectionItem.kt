package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.component.HDivider
import com.jayys.stashmap.core.designsystem.theme.stashColors
import com.jayys.stashmap.core.model.StashMapLanguage

@Composable
fun LanguageSelectionItem(
    languages: List<StashMapLanguage>,
    selectedLanguage: StashMapLanguage,
    onLanguageSelect: (StashMapLanguage) -> Unit,
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