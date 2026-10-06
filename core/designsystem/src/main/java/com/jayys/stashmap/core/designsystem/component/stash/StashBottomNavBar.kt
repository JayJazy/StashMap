package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.selectableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 하단 네비게이션 항목
 *
 * @param icon 항목 아이콘
 * @param label 항목 라벨
 * @param selected 선택 여부
 * @param onClick 클릭 콜백
 */
@Immutable
data class StashNavItem(
    val icon: ImageVector,
    val label: String,
    val selected: Boolean,
    val onClick: () -> Unit,
)

/**
 * Stash Design System 하단 네비게이션 바
 *
 * surface 배경 + 상단 divider 보더(1dp)
 *
 * 선택된 탭은 아이콘 뒤에 accentSubtle 알약(Material 3 인디케이터)이 깔림
 *
 * Scaffold 의 bottomBar 는 인셋이 적용되지 않는다. 배경만 네비게이션바 뒤로 이어지게 하려면
 * 배경 다음에 인셋을 먹어야 하므로 탭 Row 에만 [windowInsets] 를 적용한다.
 * 바텀시트·다이얼로그 안처럼 인셋이 필요 없는 자리에서는 `WindowInsets(0)` 을 넘길 것
 *
 * @param items 표시할 항목 목록
 * @param windowInsets 탭 영역이 피해 갈 인셋 (배경과 상단 보더는 영향받지 않음)
 */
@Composable
fun StashBottomNavBar(
    items: List<StashNavItem>,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets.navigationBars,
) {
    val colors = MaterialTheme.stashColorTokens

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surface)
    ) {
        // 보더는 인셋 바깥 — 가로모드 3버튼 네비에서도 화면 끝까지 닿아야 함
        StashDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets),
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
    val tint = if (item.selected) colors.accentSubtleFg else colors.fgMuted

    Column(
        modifier = modifier
            .defaultMinSize(minHeight = 64.dp)
            .selectableNoRipple(selected = item.selected, role = Role.Tab, onClick = item.onClick)
            .padding(horizontal = StashSpacing.s3, vertical = StashSpacing.s2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .clip(StashRadius.full)
                .background(if (item.selected) colors.accentSubtle else Color.Transparent)
                .padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s1),
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(StashIconSize.lg),
            )
        }

        StashText(
            text = item.label,
            role = if (item.selected) StashTextRole.CaptionStrong else StashTextRole.Caption,
            color = if (item.selected) colors.fg else tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
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
                StashNavItem(icon = StashIcons.Home, label = "홈", selected = false, onClick = {}),
                StashNavItem(icon = StashIcons.Utensils, label = "맛집", selected = true, onClick = {}),
                StashNavItem(icon = StashIcons.User, label = "마이", selected = false, onClick = {}),
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
