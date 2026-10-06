package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashColors
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/** Stash 버튼의 시각 변형(variant) */
enum class StashButtonVariant {
    /** 강조 액션 — accent 배경 + accentFg 텍스트 */
    Primary,

    /** 보조 액션 — surface 배경 + border + fg 텍스트 */
    Secondary,

    /** 약한 액션 — 투명 배경 + accent 텍스트 */
    Ghost,

    /** 파괴적 액션 — error 배경 + errorFg 텍스트 */
    Destructive,
}

/**
 * Stash 버튼의 크기 단계
 *
 * 40 / 48 / 56dp — 8dp 간격. Md 는 [StashTextField]·[StashTapRow] 와 폼에서 줄이 맞는 높이
 */
enum class StashButtonSize(
    internal val minHeight: Dp,
    internal val horizontalPadding: Dp,
    internal val iconSize: Dp,
) {
    /** 카드 안 보조 액션 — 보이는 높이만 작고 터치 타깃은 minimumInteractiveComponentSize 가 48dp 로 유지 */
    Sm(minHeight = 40.dp, horizontalPadding = StashSpacing.s3, iconSize = StashIconSize.xs),

    /** 기본 */
    Md(minHeight = 48.dp, horizontalPadding = StashSpacing.s4, iconSize = StashIconSize.sm),

    /** 화면 하단 주 액션 */
    Lg(minHeight = 56.dp, horizontalPadding = StashSpacing.s5, iconSize = StashIconSize.md),
}

/** 버튼 변형별 색상 묶음 */
private data class StashButtonColors(
    val container: Color,
    val containerPressed: Color,
    val content: Color,
    val border: Color?,
)

@Composable
private fun StashButtonVariant.colors(colors: StashColors, enabled: Boolean): StashButtonColors {
    val base = when (this) {
        StashButtonVariant.Primary -> StashButtonColors(
            container = colors.accent,
            containerPressed = colors.accentPress,
            content = colors.accentFg,
            border = null,
        )

        StashButtonVariant.Secondary -> StashButtonColors(
            container = colors.surface,
            containerPressed = colors.surface2,
            content = colors.fg,
            border = colors.border,
        )

        StashButtonVariant.Ghost -> StashButtonColors(
            container = Color.Transparent,
            containerPressed = colors.accentSubtle,
            content = colors.accentSubtleFg,
            border = null,
        )

        StashButtonVariant.Destructive -> StashButtonColors(
            container = colors.error,
            containerPressed = colors.error.copy(alpha = 0.85f),
            content = colors.errorFg,
            border = null,
        )
    }
    return if (enabled) {
        base
    } else {
        base.copy(
            container = base.container.copy(alpha = 0.4f),
            content = base.content.copy(alpha = 0.5f),
            border = base.border?.copy(alpha = 0.4f),
        )
    }
}

/**
 * Stash Design System 버튼
 *
 * @param text 버튼 라벨
 * @param onClick 클릭 콜백
 * @param variant 시각 변형 (기본 [StashButtonVariant.Primary])
 * @param size 크기 단계 (기본 [StashButtonSize.Md]) — 박스와 아이콘만 커지고 라벨은 14sp 고정
 * @param enabled 활성화 여부
 * @param leadingIcon 텍스트 앞 아이콘 (선택)
 * @param loading 로딩 상태 — true 면 라벨 대신 인디케이터 표시 + 클릭 비활성
 */
@Composable
fun StashButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: StashButtonVariant = StashButtonVariant.Primary,
    size: StashButtonSize = StashButtonSize.Md,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    loading: Boolean = false,
) {
    val colors = MaterialTheme.stashColorTokens
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val clickable = enabled && !loading
    val buttonColors = variant.colors(colors, enabled)
    val container = if (pressed && clickable) buttonColors.containerPressed else buttonColors.container

    Row(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .defaultMinSize(minHeight = size.minHeight)
            .clip(StashRadius.md)
            .background(container)
            .let { base ->
                buttonColors.border?.let { base.border(1.dp, it, StashRadius.md) } ?: base
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = clickable,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = size.horizontalPadding, vertical = StashSpacing.s2),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (loading) {
            // 라벨이 사라지므로 버튼 노드에 이름을 남긴다
            CircularProgressIndicator(
                modifier = Modifier
                    .size(size.iconSize)
                    .semantics { contentDescription = text },
                color = buttonColors.content,
                strokeWidth = 2.dp,
            )
        } else {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = buttonColors.content,
                    modifier = Modifier.size(size.iconSize),
                )
            }
            StashText(
                text = text,
                role = StashTextRole.LabelStrong,
                color = buttonColors.content,
            )
        }
    }
}

@Composable
private fun StashButtonShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s4),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StashButton(text = "기록 저장", onClick = {}, variant = StashButtonVariant.Primary)
            StashButton(text = "수정", onClick = {}, variant = StashButtonVariant.Secondary)
            StashButton(text = "더보기", onClick = {}, variant = StashButtonVariant.Ghost)
            StashButton(
                text = "삭제",
                onClick = {},
                variant = StashButtonVariant.Destructive,
                leadingIcon = StashIcons.Trash,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StashButton(text = "작게", onClick = {}, size = StashButtonSize.Sm)
            StashButton(text = "기록하기", onClick = {}, size = StashButtonSize.Md, leadingIcon = StashIcons.Plus)
            StashButton(text = "크게", onClick = {}, size = StashButtonSize.Lg)
        }
        StashButton(text = "저장 중", onClick = {}, loading = true)
        StashButton(text = "비활성", onClick = {}, enabled = false)
    }
}

@Preview(showBackground = true)
@Composable
private fun StashButtonLightPreview() {
    StashTheme(darkTheme = false) {
        StashButtonShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashButtonDarkPreview() {
    StashTheme(darkTheme = true) {
        StashButtonShowcase()
    }
}
