package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * Stash Design System 칩.
 *
 * 기본은 chipBg/chipFg, 선택 시 accentSubtle/accentSubtleFg 를 사용한다.
 * full 반경의 알약 형태이며 [onClick] 이 있을 때만 클릭 가능하다.
 *
 * @param label 칩 라벨
 * @param selected 선택 상태
 * @param onClick 클릭 콜백 (null 이면 비클릭)
 * @param leadingIcon 라벨 앞 아이콘 (선택)
 */
@Composable
fun StashChip(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    leadingIcon: ImageVector? = null,
) {
    val colors = MaterialTheme.stashColorTokens
    val container = if (selected) colors.accentSubtle else colors.chipBg
    val content = if (selected) colors.accentSubtleFg else colors.chipFg

    val base = modifier
        .clip(StashRadius.full)
        .background(container)
        .semantics { this.selected = selected }
    val withClick = if (onClick != null) {
        base.clickableNoRipple(role = Role.Button, onClick = onClick)
    } else {
        base
    }

    Row(
        modifier = withClick.padding(horizontal = StashSpacing.s3, vertical = StashSpacing.s1),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = content,
                modifier = Modifier.size(16.dp),
            )
        }
        StashText(
            text = label,
            role = StashTextRole.Label,
            color = content,
        )
    }
}

@Composable
private fun StashChipShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2)) {
            StashChip(label = "기본", onClick = {})
            StashChip(label = "선택됨", selected = true, onClick = {})
            StashChip(label = "아이콘", leadingIcon = Icons.Default.Favorite, onClick = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StashChipLightPreview() {
    StashTheme(darkTheme = false) {
        StashChipShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashChipDarkPreview() {
    StashTheme(darkTheme = true) {
        StashChipShowcase()
    }
}
