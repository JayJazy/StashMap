package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.selectableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.style

/**
 * 등록 화면에서 평가 하나를 고르는 큰 선택 카드
 *
 * 선택되면 카드 전체가 상태색으로 물들고(연한 배경 + 상태색 보더), 아이콘 타일이 꽉 찬 상태색으로 바뀜
 *
 * @param state 이 카드가 나타내는 평가 상태
 * @param selected 선택 여부
 * @param onClick 선택 콜백
 */
@Composable
fun StashEvalSelectCard(
    state: StashEvalState,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens
    val style = state.style

    Column(
        modifier = modifier
            .clip(StashRadius.lg)
            .background(if (selected) style.subtle else colors.surface)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) style.solid else colors.border,
                shape = StashRadius.lg,
            )
            .selectableNoRipple(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s3),
    ) {
        Box(
            modifier = Modifier
                .size(EvalIconTileSize)
                .clip(StashRadius.md)
                .background(if (selected) style.solid else style.subtle),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = style.icon,
                contentDescription = null,
                tint = if (selected) style.onSolid else style.solid,
                modifier = Modifier.size(StashIconSize.xl),
            )
        }

        StashText(
            text = style.label,
            role = StashTextRole.LabelStrong,
            color = if (selected) style.subtleFg else colors.fg,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/**
 * 평가 4상태를 2x2 로 늘어놓은 선택 그리드 — 등록 화면의 평가 입력부
 *
 * @param selected 현재 선택된 상태 (null 이면 미선택)
 * @param onSelect 선택 콜백
 */
@Composable
fun StashEvalSelectGrid(
    selected: StashEvalState?,
    onSelect: (StashEvalState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s3),
    ) {
        StashEvalState.entries.chunked(2).forEach { rowStates ->
            Row(horizontalArrangement = Arrangement.spacedBy(StashSpacing.s3)) {
                rowStates.forEach { state ->
                    StashEvalSelectCard(
                        state = state,
                        selected = state == selected,
                        onClick = { onSelect(state) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

private val EvalIconTileSize = 44.dp

@Composable
private fun StashEvalSelectGridShowcase() {
    var selected by remember { mutableStateOf<StashEvalState?>(StashEvalState.Favorite) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        StashEvalSelectGrid(selected = selected, onSelect = { selected = it })
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashEvalSelectGridLightPreview() {
    StashTheme(darkTheme = false) {
        StashEvalSelectGridShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashEvalSelectGridDarkPreview() {
    StashTheme(darkTheme = true) {
        StashEvalSelectGridShowcase()
    }
}
