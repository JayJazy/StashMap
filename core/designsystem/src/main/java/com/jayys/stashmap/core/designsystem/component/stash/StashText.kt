package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography

/**
 * Stash Design System 의 기본 텍스트 컴포저블.
 *
 * 역할([role])에 해당하는 [TextStyle] 을 [stashTypography] 에서 가져오고,
 * [color] 가 [Color.Unspecified] 이면 역할의 기본 색상 토큰(fg / fgMuted)으로 해석한다.
 * `Overline` 역할은 텍스트를 대문자로 변환한다.
 *
 * @param text 표시할 문자열
 * @param role 텍스트 역할 (기본 [StashTextRole.Body])
 * @param color 명시 색상. [Color.Unspecified] 이면 역할 기본 토큰 사용
 * @param maxLines 최대 줄 수
 * @param overflow 넘침 처리 방식
 * @param textAlign 텍스트 정렬
 */
@Composable
fun StashText(
    text: String,
    modifier: Modifier = Modifier,
    role: StashTextRole = StashTextRole.Body,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    textAlign: TextAlign? = null,
) {
    val colors = MaterialTheme.stashColorTokens
    val baseStyle = MaterialTheme.stashTypography[role]

    val resolvedColor = if (color != Color.Unspecified) {
        color
    } else if (role.defaultUsesMuted) {
        colors.fgMuted
    } else {
        colors.fg
    }

    val resolvedText = if (role == StashTextRole.Overline) text.uppercase() else text

    val style = baseStyle.merge(
        TextStyle(
            color = resolvedColor,
            textAlign = textAlign ?: TextAlign.Unspecified,
        ),
    )

    BasicText(
        text = resolvedText,
        modifier = modifier,
        style = style,
        maxLines = maxLines,
        overflow = overflow,
    )
}

/**
 * 타입 역할(role) 스케일을 한눈에 보여주는 specimen 컬럼.
 */
@Composable
private fun StashTextSpecimen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s2),
    ) {
        StashText(text = "Display", role = StashTextRole.Display)
        StashText(text = "H1 Heading", role = StashTextRole.H1)
        StashText(text = "H2 Heading", role = StashTextRole.H2)
        StashText(text = "H3 Heading", role = StashTextRole.H3)
        StashText(text = "Body Large 본문", role = StashTextRole.BodyLg)
        StashText(text = "Body 본문 텍스트", role = StashTextRole.Body)
        StashText(text = "Body Small 작은 본문", role = StashTextRole.BodySm)
        StashText(text = "Label 라벨", role = StashTextRole.Label)
        StashText(text = "Caption 캡션", role = StashTextRole.Caption)
        StashText(text = "overline", role = StashTextRole.Overline)
        StashText(text = "mono 1234", role = StashTextRole.Mono)
    }
}

@Preview(showBackground = true, heightDp = 520)
@Composable
private fun StashTextLightPreview() {
    StashTheme(darkTheme = false) {
        StashTextSpecimen()
    }
}

@Preview(showBackground = true, heightDp = 520)
@Composable
private fun StashTextDarkPreview() {
    StashTheme(darkTheme = true) {
        StashTextSpecimen()
    }
}
