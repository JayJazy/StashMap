package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * Stash Design System 구분선
 *
 * 카드 안에 행을 여러 개 쌓을 때 그 사이를 가름. 색은 divider 토큰 고정
 *
 * @param thickness 선 굵기
 */
@Composable
fun StashDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
) {
    HorizontalDivider(
        modifier = modifier.fillMaxWidth(),
        thickness = thickness,
        color = MaterialTheme.stashColorTokens.divider,
    )
}

@Composable
private fun StashDividerShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        StashCard(contentPadding = PaddingValues(vertical = StashSpacing.s2)) {
            StashText(
                text = "위 행",
                role = StashTextRole.Body,
                modifier = Modifier.padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s3),
            )
            StashDivider()
            StashText(
                text = "아래 행",
                role = StashTextRole.Body,
                modifier = Modifier.padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s3),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashDividerLightPreview() {
    StashTheme(darkTheme = false) {
        StashDividerShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashDividerDarkPreview() {
    StashTheme(darkTheme = true) {
        StashDividerShowcase()
    }
}
