package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalStyle
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashMinTouchTarget
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.style

/** 평가 아이콘 칩의 크기 단계 */
enum class StashEvalChipSize(internal val box: Dp, internal val icon: Dp, internal val shape: RoundedCornerShape) {
    /** 리스트 메타 줄처럼 좁은 자리 */
    Sm(box = 28.dp, icon = StashIconSize.xs, shape = StashRadius.sm),

    /** 카드 트레일링 등 기본 */
    Md(box = 36.dp, icon = StashIconSize.md, shape = StashRadius.md),

    /** 상세 헤더처럼 평가가 주인공인 자리 */
    Lg(box = 48.dp, icon = StashIconSize.xl, shape = StashRadius.md),
}

/**
 * 평가 상태를 아이콘 하나로 보여주는 칩 — 리스트·카드·상세 어디서나 같은 모양
 *
 * 연한 상태색 배경 + 진한 상태색 아이콘
 *
 * @param state 표시할 평가 상태
 * @param size 칩 크기 단계
 * @param contentDescription 접근성 설명 — 기본은 상태 라벨. 옆에 같은 라벨을 따로 그리는 자리에서는 null 을 명시해 중복 announce 를 막을 것
 * @param onClick 클릭 콜백 (null 이면 표시 전용)
 */
@Composable
fun StashEvalIconChip(
    state: StashEvalState,
    modifier: Modifier = Modifier,
    size: StashEvalChipSize = StashEvalChipSize.Md,
    contentDescription: String? = state.style.label,
    onClick: (() -> Unit)? = null,
) {
    val style = state.style

    // 클릭 가능하면 칩 크기는 그대로 두고 터치 타깃만 48dp 로 넓힘
    val touchTarget = if (onClick == null) {
        Modifier
    } else {
        Modifier
            .sizeIn(minWidth = StashMinTouchTarget, minHeight = StashMinTouchTarget)
            .clickableNoRipple(role = Role.Button, onClick = onClick)
    }

    Box(modifier = modifier.then(touchTarget), contentAlignment = Alignment.Center) {
        EvalChipBox(style = style, size = size, contentDescription = contentDescription)
    }
}

@Composable
private fun EvalChipBox(
    style: StashEvalStyle,
    size: StashEvalChipSize,
    contentDescription: String?,
) {
    Box(
        modifier = Modifier
            .size(size.box)
            .clip(size.shape)
            .background(style.subtle),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = style.icon,
            contentDescription = contentDescription,
            tint = style.solid,
            modifier = Modifier.size(size.icon),
        )
    }
}

@Composable
private fun StashEvalIconChipShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s3),
    ) {
        StashEvalChipSize.entries.forEach { size ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StashEvalState.entries.forEach { state ->
                    StashEvalIconChip(state = state, size = size)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StashEvalIconChipLightPreview() {
    StashTheme(darkTheme = false) {
        StashEvalIconChipShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashEvalIconChipDarkPreview() {
    StashTheme(darkTheme = true) {
        StashEvalIconChipShowcase()
    }
}
