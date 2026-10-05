package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 목록이 비었을 때 보여주는 안내 — 아이콘 + 제목 + 설명 + 행동 유도 버튼
 *
 * @param title 한 줄 제목
 * @param description 보조 설명
 * @param icon 원형 배경 안에 넣을 아이콘
 * @param actionLabel 버튼 라벨 ([onAction] 과 함께 줘야 버튼이 보임)
 * @param onAction 버튼 클릭 콜백
 * @param actionIcon 버튼 아이콘 (null 이면 라벨만)
 */
@Composable
fun StashEmptyState(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: ImageVector = StashIcons.Utensils,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    actionIcon: ImageVector? = StashIcons.Plus,
) {
    val colors = MaterialTheme.stashColorTokens

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = StashSpacing.s6, vertical = StashSpacing.s10),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s4),
    ) {
        Box(
            modifier = Modifier
                .size(CircleSize)
                .clip(CircleShape)
                .background(colors.surface2),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.fgSubtle,
                modifier = Modifier.size(StashIconSize.xxl),
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(StashSpacing.s2),
        ) {
            StashText(text = title, role = StashTextRole.H3, textAlign = TextAlign.Center)

            if (description != null) {
                StashText(
                    text = description,
                    role = StashTextRole.BodySm,
                    color = colors.fgMuted,
                    textAlign = TextAlign.Center,
                )
            }
        }

        if (actionLabel != null && onAction != null) {
            StashButton(text = actionLabel, onClick = onAction, leadingIcon = actionIcon)
        }
    }
}

private val CircleSize = 80.dp

@Composable
private fun StashEmptyStateShowcase() {
    Box(modifier = Modifier.background(MaterialTheme.stashColorTokens.bg)) {
        StashEmptyState(
            title = "첫 맛집을 기록해보세요",
            description = "다녀온 가게를 남기면 다시 갈 집이 한눈에 보여요.",
            actionLabel = "기록하기",
            onAction = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashEmptyStateLightPreview() {
    StashTheme(darkTheme = false) {
        StashEmptyStateShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashEmptyStateDarkPreview() {
    StashTheme(darkTheme = true) {
        StashEmptyStateShowcase()
    }
}
