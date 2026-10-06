package com.jayys.stashmap.feature.stash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/** 스크롤 밖에 고정되는 화면 제목 — 홈 워드마크 헤더와 같은 높이 */
@Composable
fun TitleHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.stashColorTokens.bg)
            .heightIn(min = HeaderMinHeight)
            .padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StashText(
            text = title,
            modifier = Modifier.semantics { heading() },
            role = StashTextRole.H1,
        )
    }
}

private val HeaderMinHeight = 56.dp

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun TitleHeaderLightPreview() {
    StashTheme(darkTheme = false) {
        TitleHeader(title = "맛집")
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun TitleHeaderDarkPreview() {
    StashTheme(darkTheme = true) {
        TitleHeader(title = "맛집")
    }
}
