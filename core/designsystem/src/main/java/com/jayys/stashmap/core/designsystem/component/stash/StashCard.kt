package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashShadowLevel
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashShadow

/**
 * Stash Design System 카드 컨테이너.
 *
 * surface 배경, 1dp 보더, lg 반경, 라이트 그림자(sm)를 적용한다.
 * [onClick] 이 지정된 경우에만 클릭 가능하다.
 *
 * @param onClick 클릭 콜백 (null 이면 비클릭)
 * @param content 카드 내부 콘텐츠
 */
@Composable
fun StashCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = MaterialTheme.stashColorTokens

    val base = modifier
        // 카드 그림자 색은 테마(라이트/다크)에 맞춰 자동 선택된다.
        .stashShadow(level = StashShadowLevel.Sm, shape = StashRadius.lg)
        .clip(StashRadius.lg)
        .background(colors.surface)
        .border(1.dp, colors.border, StashRadius.lg)

    val withClick = if (onClick != null) {
        base.clickableNoRipple(role = Role.Button, onClick = onClick)
    } else {
        base
    }

    Column(
        modifier = withClick.padding(StashSpacing.s4),
        content = content,
    )
}

@Composable
private fun StashCardShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        StashCard(onClick = {}) {
            StashText(text = "카드 제목", role = StashTextRole.H3)
            StashText(text = "카드 본문 설명입니다.", role = StashTextRole.BodySm)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StashCardLightPreview() {
    StashTheme(darkTheme = false) {
        StashCardShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashCardDarkPreview() {
    StashTheme(darkTheme = true) {
        StashCardShowcase()
    }
}
