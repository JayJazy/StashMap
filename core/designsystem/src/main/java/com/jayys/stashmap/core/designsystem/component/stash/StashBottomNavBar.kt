package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 하단 네비게이션 항목.
 *
 * @param icon 항목 아이콘
 * @param label 항목 라벨
 * @param selected 선택 여부
 * @param onClick 클릭 콜백
 */
data class StashNavItem(
    val icon: ImageVector,
    val label: String,
    val selected: Boolean,
    val onClick: () -> Unit,
)

/**
 * Stash Design System 하단 네비게이션 바.
 *
 * surface 배경 + 상단 divider 보더(1dp)를 가지며, 선택 항목은 accent, 비선택은 fgMuted 로 표시한다.
 *
 * @param items 표시할 항목 목록
 */
@Composable
fun StashBottomNavBar(
    items: List<StashNavItem>,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surface)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = colors.divider
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEach { item ->
                StashNavBarItem(item = item, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun StashNavBarItem(
    item: StashNavItem,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens
    val tint = if (item.selected) colors.accent else colors.fgMuted

    Column(
        modifier = modifier
            .defaultMinSize(minHeight = 56.dp)
            .clickableNoRipple(role = Role.Tab, onClick = item.onClick)
            .semantics { selected = item.selected }
            .padding(horizontal = StashSpacing.s3, vertical = StashSpacing.s2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = tint,
            modifier = Modifier.size(24.dp),
        )

        StashText(
            text = item.label,
            role = StashTextRole.Caption,
            color = tint,
            maxLines = 1,
            modifier = Modifier.padding(top = StashSpacing.s1),
        )
    }
}

@Composable
private fun StashBottomNavBarShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
    ) {
        StashBottomNavBar(
            items = listOf(
                StashNavItem(icon = Icons.Default.Home, label = "홈", selected = true, onClick = {}),
                StashNavItem(icon = Icons.Default.Search, label = "검색", selected = false, onClick = {}),
                StashNavItem(icon = Icons.Default.Person, label = "프로필", selected = false, onClick = {}),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StashBottomNavBarLightPreview() {
    StashTheme(darkTheme = false) {
        StashBottomNavBarShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashBottomNavBarDarkPreview() {
    StashTheme(darkTheme = true) {
        StashBottomNavBarShowcase()
    }
}
