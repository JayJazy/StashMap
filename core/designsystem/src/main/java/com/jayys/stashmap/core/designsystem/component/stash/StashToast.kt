package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashColors
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashShadowLevel
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashShadow

/**
 * Stash 토스트 변형.
 */
enum class StashToastVariant { Neutral, Success, Warning, Error, Info }

private data class StashToastColors(
    val container: Color,
    val content: Color,
    val border: Color?,
    val action: Color,
)

@Composable
private fun StashToastVariant.colors(colors: StashColors): StashToastColors = when (this) {
    StashToastVariant.Neutral -> StashToastColors(
        container = colors.surface,
        content = colors.fg,
        border = colors.border,
        action = colors.accent,
    )

    StashToastVariant.Success -> StashToastColors(
        container = colors.successSubtle,
        content = colors.successSubtleFg,
        border = null,
        action = colors.successSubtleFg,
    )

    StashToastVariant.Warning -> StashToastColors(
        container = colors.warningSubtle,
        content = colors.warningSubtleFg,
        border = null,
        action = colors.warningSubtleFg,
    )

    StashToastVariant.Error -> StashToastColors(
        container = colors.errorSubtle,
        content = colors.errorSubtleFg,
        border = null,
        action = colors.errorSubtleFg,
    )

    StashToastVariant.Info -> StashToastColors(
        container = colors.infoSubtle,
        content = colors.infoSubtleFg,
        border = null,
        action = colors.infoSubtleFg,
    )
}

/**
 * Stash Design System 토스트.
 *
 * Neutral 은 surface/fg + border 를, 그 외 변형은 *Subtle 배경 + *SubtleFg 텍스트를 사용한다.
 * md 반경과 md 그림자를 적용하며, [actionLabel] 이 있으면 우측에 액션 텍스트를 노출한다.
 *
 * @param message 토스트 메시지
 * @param variant 시각 변형 (기본 [StashToastVariant.Neutral])
 * @param actionLabel 액션 라벨 (선택)
 * @param onAction 액션 클릭 콜백
 */
@Composable
fun StashToast(
    message: String,
    modifier: Modifier = Modifier,
    variant: StashToastVariant = StashToastVariant.Neutral,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val colors = MaterialTheme.stashColorTokens
    val toastColors = variant.colors(colors)

    val base = modifier
        .stashShadow(level = StashShadowLevel.Md, shape = StashRadius.md)
        .clip(StashRadius.md)
        .background(toastColors.container)
        .let { b -> toastColors.border?.let { b.border(1.dp, it, StashRadius.md) } ?: b }
        .padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s3)

    Row(
        modifier = base,
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StashText(
            text = message,
            role = StashTextRole.Label,
            color = toastColors.content,
        )
        if (actionLabel != null) {
            StashText(
                text = actionLabel,
                role = StashTextRole.Label,
                color = toastColors.action,
                modifier = Modifier.clickableNoRipple(
                    enabled = onAction != null,
                    role = Role.Button,
                ) { onAction?.invoke() },
            )
        }
    }
}

@Composable
private fun StashToastShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s4),
    ) {
        StashToast(message = "Neutral 토스트", variant = StashToastVariant.Neutral, actionLabel = "실행", onAction = {})
        StashToast(message = "Success 토스트", variant = StashToastVariant.Success)
        StashToast(message = "Warning 토스트", variant = StashToastVariant.Warning)
        StashToast(message = "Error 토스트", variant = StashToastVariant.Error)
        StashToast(message = "Info 토스트", variant = StashToastVariant.Info)
    }
}

@Preview(showBackground = true, heightDp = 360)
@Composable
private fun StashToastLightPreview() {
    StashTheme(darkTheme = false) {
        StashToastShowcase()
    }
}

@Preview(showBackground = true, heightDp = 360)
@Composable
private fun StashToastDarkPreview() {
    StashTheme(darkTheme = true) {
        StashToastShowcase()
    }
}
