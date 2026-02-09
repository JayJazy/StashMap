package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.compose.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.TextStyleEnum
import com.jayys.stashmap.core.designsystem.theme.stashColors
import com.jayys.stashmap.core.designsystem.theme.typography
import com.jayys.stashmap.core.model.StashMapLanguage

@Composable
fun LanguageItem(
    language: StashMapLanguage,
    isSelected: Boolean,
    onLanguageSelect: (StashMapLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickableNoRipple { onLanguageSelect(language) }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = language.flag,
            style = typography(TextStyleEnum.Body2)
        )

        Text(
            text = language.displayName,
            style = typography(TextStyleEnum.Body2),
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onLanguageSelect(language) },
            modifier = Modifier.size(20.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.stashColors.greenDark1,
                checkmarkColor = MaterialTheme.stashColors.bgColor
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLanguageItem() {
    LanguageItem(
        language = StashMapLanguage.KOREAN,
        isSelected = false,
        onLanguageSelect = {}
    )
}