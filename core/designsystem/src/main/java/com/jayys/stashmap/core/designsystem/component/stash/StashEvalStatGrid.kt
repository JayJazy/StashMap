package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.selectableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.style

/**
 * 맛집 탭 상단의 평가 4상태 통계 그리드 (2x2)
 *
 * 칸을 누르면 그 상태로 리스트를 거르는 필터로 쓸 수 있음
 *
 * @param favoriteCount 다시 먹을래요 개수
 * @param averageCount 보통이에요 개수
 * @param avoidCount 다신 안 먹을래요 개수
 * @param wantToTryCount 먹어보고 싶어요 개수
 * @param selectedState 현재 필터로 걸린 상태 (null 이면 전체)
 * @param onStateClick 칸 클릭 콜백 (null 이면 표시 전용)
 */
@Composable
fun StashEvalStatGrid(
    favoriteCount: Int,
    averageCount: Int,
    avoidCount: Int,
    wantToTryCount: Int,
    modifier: Modifier = Modifier,
    selectedState: StashEvalState? = null,
    onStateClick: ((StashEvalState) -> Unit)? = null,
) {
    val cells = listOf(
        StashEvalState.Favorite to favoriteCount,
        StashEvalState.Average to averageCount,
        StashEvalState.Avoid to avoidCount,
        StashEvalState.WantToTry to wantToTryCount,
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s2),
    ) {
        cells.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2)) {
                row.forEach { (state, count) ->
                    StatCell(
                        state = state,
                        count = count,
                        selected = state == selectedState,
                        onClick = if (onStateClick == null) null else ({ onStateClick(state) }),
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCell(
    state: StashEvalState,
    count: Int,
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens
    val style = state.style

    val base = modifier
        .clip(StashRadius.lg)
        .background(if (selected) style.subtle else colors.surface)
        .border(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) style.solid else colors.border,
            shape = StashRadius.lg,
        )
    val withClick = if (onClick != null) {
        base
            .selectableNoRipple(selected = selected, role = Role.RadioButton, onClick = onClick)
    } else {
        base
    }

    Column(
        modifier = withClick.padding(StashSpacing.s3),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s2),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StashEvalIconChip(state = state, size = StashEvalChipSize.Sm, contentDescription = null)
            StashText(
                text = style.label,
                role = StashTextRole.Caption,
                color = colors.fgMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        StashText(
            text = count.toString(),
            role = StashTextRole.H1,
            color = if (selected) style.subtleFg else colors.fg,
        )
    }
}

@Composable
private fun StashEvalStatGridShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        StashEvalStatGrid(
            favoriteCount = 12,
            averageCount = 5,
            avoidCount = 3,
            wantToTryCount = 8,
            selectedState = StashEvalState.Favorite,
            onStateClick = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashEvalStatGridLightPreview() {
    StashTheme(darkTheme = false) {
        StashEvalStatGridShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashEvalStatGridDarkPreview() {
    StashTheme(darkTheme = true) {
        StashEvalStatGridShowcase()
    }
}
