package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashShadowLevel
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashShadow

/**
 * 빠른 기록 FAB — 맛집 탭·홈에서 등록 화면으로 들어가는 진입점
 *
 * @param onClick 클릭 콜백
 * @param contentDescription 접근성 설명 — [label] 이 없으면 아이콘의 이름이 되고,
 *   있으면 보이는 라벨이 이름이 되므로(WCAG Label in Name) 동작 설명으로 쓰인다
 * @param icon 표시할 아이콘
 * @param label 아이콘 옆 라벨 — 주면 extended 모양, null 이면 정사각
 */
@Composable
fun StashFab(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = StashIcons.Plus,
    label: String? = null,
) {
    val colors = MaterialTheme.stashColorTokens

    val container = modifier
        .then(if (label == null) Modifier.size(FabSize) else Modifier.height(FabSize))
        .stashShadow(level = StashShadowLevel.Lg, shape = StashRadius.xl)
        .clip(StashRadius.xl)
        .background(colors.accent)
        .clickableNoRipple(
            role = Role.Button,
            // extended 는 라벨이 이름을 맡으므로, 넘겨받은 설명은 동작 쪽에 실어 버리지 않는다
            onClickLabel = contentDescription.takeIf { label != null },
            onClick = onClick,
        )

    if (label == null) {
        Box(modifier = container, contentAlignment = Alignment.Center) {
            FabIcon(icon = icon, contentDescription = contentDescription)
        }
    } else {
        Row(
            // 아이콘 쪽 여백을 한 칸 줄여 광학적으로 맞춤
            modifier = container.padding(start = StashSpacing.s4, end = StashSpacing.s5),
            horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 라벨이 이름을 대신 읽어주므로 아이콘은 장식 취급 — 안 그러면 두 번 읽힌다
            FabIcon(icon = icon, contentDescription = null)
            StashText(text = label, role = StashTextRole.LabelStrong, color = colors.accentFg)
        }
    }
}

@Composable
private fun FabIcon(icon: ImageVector, contentDescription: String?) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        tint = MaterialTheme.stashColorTokens.accentFg,
        modifier = Modifier.size(StashIconSize.xl),
    )
}

private val FabSize = 56.dp

@Composable
private fun StashFabShowcase() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s6),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StashFab(onClick = {}, contentDescription = "맛집 기록하기")
        StashFab(onClick = {}, contentDescription = "맛집 기록하기", label = "기록")
    }
}

@Preview(showBackground = true)
@Composable
private fun StashFabLightPreview() {
    StashTheme(darkTheme = false) {
        StashFabShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashFabDarkPreview() {
    StashTheme(darkTheme = true) {
        StashFabShowcase()
    }
}
