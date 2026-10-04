package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.toggleableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * [StashSegmentedSelect] 의 항목
 *
 * @param label 항목 라벨
 * @param icon 라벨 앞 아이콘 (선택)
 */
@Immutable
data class StashSegmentItem(
    val label: String,
    val icon: ImageVector? = null,
)

/**
 * 가로로 균등 분할된 선택 세그먼트 — 이용 형태(배달/포장/매장)처럼 후보가 적을 때
 *
 * 다중 선택이므로 토글 동작은 호출부가 [selectedIndices] 로 결정.
 * 선택을 인덱스로 가리키므로 [items] 는 고정 목록이어야 한다 (중간에 정렬·필터가 바뀌면 선택이 어긋남)
 *
 * @param items 표시할 항목들
 * @param selectedIndices 선택된 항목 인덱스 집합
 * @param onToggle 항목 토글 콜백
 */
@Composable
fun StashSegmentedSelect(
    items: List<StashSegmentItem>,
    selectedIndices: Set<Int>,
    onToggle: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
    ) {
        items.forEachIndexed { index, item ->
            SegmentCell(
                item = item,
                selected = index in selectedIndices,
                onClick = { onToggle(index) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun SegmentCell(
    item: StashSegmentItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens
    val content = if (selected) colors.accent else colors.fg

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp)
            .clip(StashRadius.md)
            .background(if (selected) colors.accentSubtle else colors.surface)
            .border(
                width = 1.dp,
                color = if (selected) colors.accent else colors.border,
                shape = StashRadius.md,
            )
            .toggleableNoRipple(value = selected, role = Role.Checkbox) { onClick() }
            .padding(horizontal = StashSpacing.s3, vertical = StashSpacing.s2),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (item.icon != null) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = content,
                modifier = Modifier.size(StashIconSize.sm),
            )
        }
        StashText(
            text = item.label,
            role = StashTextRole.LabelStrong,
            color = content,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun StashSegmentedSelectShowcase() {
    var selected by remember { mutableStateOf(setOf(1)) }
    val items = listOf(
        StashSegmentItem(label = "배달", icon = StashIcons.Truck),
        StashSegmentItem(label = "포장", icon = StashIcons.ShoppingBag),
        StashSegmentItem(label = "매장", icon = StashIcons.Utensils),
    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        StashSegmentedSelect(
            items = items,
            selectedIndices = selected,
            onToggle = { index ->
                selected = if (index in selected) selected - index else selected + index
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashSegmentedSelectLightPreview() {
    StashTheme(darkTheme = false) {
        StashSegmentedSelectShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashSegmentedSelectDarkPreview() {
    StashTheme(darkTheme = true) {
        StashSegmentedSelectShowcase()
    }
}
