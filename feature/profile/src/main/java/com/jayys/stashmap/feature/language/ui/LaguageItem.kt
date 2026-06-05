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
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography
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
            color = MaterialTheme.stashColorTokens.fg,
            style = MaterialTheme.stashTypography.body
        )

        Text(
            text = language.displayName,
            color = MaterialTheme.stashColorTokens.fg,
            style = MaterialTheme.stashTypography.body,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onLanguageSelect(language) },
            modifier = Modifier.size(20.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.stashColorTokens.success,
                checkmarkColor = MaterialTheme.stashColorTokens.fgOnAccent
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