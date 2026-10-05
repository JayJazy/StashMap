package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.selectableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * Stash Design System 칩
 *
 * - 기본은 회색 알약, 선택되면 배경을 비우고 accent 테두리로 바뀜
 * - 필터처럼 여러 개를 켜고 끄는 자리를 가정 (role = Checkbox)
 * - [onClick] 이 있을 때만 클릭 가능
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
    val content = if (selected) colors.accent else colors.chipFg

    val base = modifier
        .minimumInteractiveComponentSize()
        .defaultMinSize(minHeight = MinHeight)
        .clip(StashRadius.full)
        .background(if (selected) Color.Transparent else colors.chipBg)
        .let { if (selected) it.border(1.5.dp, colors.accent, StashRadius.full) else it }
    val withClick = if (onClick != null) {
        base.selectableNoRipple(selected = selected, role = Role.Checkbox, onClick = onClick)
    } else {
        base
    }

    Row(
        modifier = withClick.padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s2),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s1),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = content,
                modifier = Modifier.size(StashIconSize.xs),
            )
        }
        StashText(
            text = label,
            role = StashTextRole.Label,
            color = content,
        )
    }
}

/** 보이는 높이. 터치 타깃은 minimumInteractiveComponentSize 가 48dp 로 보장 */
private val MinHeight = 40.dp

@Composable
private fun StashChipShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2)) {
            StashChip(label = "선택됨", selected = true, onClick = {})
            StashChip(label = "한식", onClick = {})
            StashChip(label = "일식", onClick = {})
            StashChip(label = "양식", onClick = {})
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
