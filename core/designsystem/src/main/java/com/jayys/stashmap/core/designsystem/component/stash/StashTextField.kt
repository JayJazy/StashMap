package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography

/**
 * Stash Design System 텍스트 필드.
 *
 * Material3 스타일을 피하기 위해 [BasicTextField] 위에 토큰 기반 데코레이션을 직접 구성한다.
 * 포커스 시 accent 보더 + accentRing, 에러 시 error 보더 + errorSubtleFg 보조 텍스트를 적용한다.
 *
 * @param value 현재 입력값
 * @param onValueChange 입력 변경 콜백
 * @param placeholder 비어 있을 때 표시할 안내 문구
 * @param label 필드 상단 라벨
 * @param isError 에러 상태 여부
 * @param supportingText 하단 보조 문구
 * @param singleLine 단일 행 여부
 * @param minLines 최소 행 수
 * @param enabled 활성화 여부
 */
@Composable
fun StashTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    label: String? = null,
    isError: Boolean = false,
    supportingText: String? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    enabled: Boolean = true,
) {
    val colors = MaterialTheme.stashColorTokens
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    val borderColor = when {
        isError -> colors.error
        focused -> colors.accent
        else -> colors.fieldBorder
    }
    val borderWidth = if (focused || isError) 2.dp else 1.dp
    val ringColor = if (focused && !isError) colors.accentRing else colors.fieldBg

    Column(
        modifier = modifier
    ) {
        if (label != null) {
            StashText(
                text = label,
                role = StashTextRole.Label,
                color = colors.fg,
                modifier = Modifier.padding(bottom = StashSpacing.s1),
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .clip(StashRadius.md)
                .background(if (enabled) colors.fieldBg else colors.surface2)
                // 포커스 링: 바깥쪽 옅은 보더로 표현
                .border(width = if (focused && !isError) 3.dp else 0.dp, color = ringColor, shape = StashRadius.md)
                .border(width = borderWidth, color = borderColor, shape = StashRadius.md)
                .defaultMinSize(minHeight = 44.dp),
            enabled = enabled,
            textStyle = MaterialTheme.stashTypography[StashTextRole.Body].copy(color = colors.fg),
            cursorBrush = SolidColor(colors.accent),
            singleLine = singleLine,
            minLines = minLines,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.padding(
                        horizontal = StashSpacing.s3,
                        vertical = StashSpacing.s3,
                    ),
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        StashText(
                            text = placeholder,
                            role = StashTextRole.Body,
                            color = colors.fgSubtle,
                        )
                    }
                    innerTextField()
                }
            },
        )

        if (supportingText != null) {
            StashText(
                text = supportingText,
                role = StashTextRole.Caption,
                color = if (isError) colors.errorSubtleFg else colors.fgMuted,
                modifier = Modifier.padding(top = StashSpacing.s1, start = StashSpacing.s1),
            )
        }
    }
}

@Composable
private fun StashTextFieldShowcase() {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s4),
    ) {
        StashTextField(
            value = text,
            onValueChange = { text = it },
            label = "라벨",
            placeholder = "입력하세요",
            supportingText = "도움말 텍스트",
        )
        StashTextField(
            value = "잘못된 값",
            onValueChange = {},
            label = "에러 필드",
            isError = true,
            supportingText = "에러 메시지",
        )
        StashTextField(
            value = "비활성 값",
            onValueChange = {},
            label = "비활성 필드",
            enabled = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StashTextFieldLightPreview() {
    StashTheme(darkTheme = false) {
        StashTextFieldShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashTextFieldDarkPreview() {
    StashTheme(darkTheme = true) {
        StashTextFieldShowcase()
    }
}
