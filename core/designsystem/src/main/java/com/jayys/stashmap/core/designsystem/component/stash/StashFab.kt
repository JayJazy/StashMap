package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashShadow

/**
 * 빠른 기록 FAB — 맛집 탭·홈에서 등록 화면으로 들어가는 진입점
 *
 * @param onClick 클릭 콜백
 * @param contentDescription 접근성 설명 — 아이콘뿐이라 이게 없으면 TalkBack 에서 읽히지 않는다
 * @param icon 표시할 아이콘
 */
@Composable
fun StashFab(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = StashIcons.Plus,
) {
    val colors = MaterialTheme.stashColorTokens

    Box(
        modifier = modifier
            .size(FabSize)
            .stashShadow(level = StashShadowLevel.Lg, shape = StashRadius.xl)
            .clip(StashRadius.xl)
            .background(colors.accent)
            .clickableNoRipple(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = colors.accentFg,
            modifier = Modifier.size(StashIconSize.xl),
        )
    }
}

private val FabSize = 56.dp

@Composable
private fun StashFabShowcase() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s6),
    ) {
        StashFab(onClick = {}, contentDescription = "맛집 기록하기")
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
