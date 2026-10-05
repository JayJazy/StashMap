package com.jayys.stashmap.feature.language.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.selectableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
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
            .selectableNoRipple(selected = isSelected, role = Role.RadioButton) { onLanguageSelect(language) }
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

        // 선택 여부는 행의 selectable 이 전달하므로 여기선 표시만 — 자리는 늘 차지해 줄이 흔들리지 않게
        Box(modifier = Modifier.size(StashIconSize.md)) {
            if (isSelected) {
                Icon(
                    imageVector = StashIcons.Check,
                    contentDescription = null,
                    tint = MaterialTheme.stashColorTokens.success
                )
            }
        }
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