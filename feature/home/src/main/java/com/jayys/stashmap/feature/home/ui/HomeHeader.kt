package com.jayys.stashmap.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/** 홈 상단 워드마크 헤더 — 브랜드명이라 번역하지 않는다 */
@Composable
fun HomeHeader(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.stashColorTokens

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.bg)
            .heightIn(min = HeaderMinHeight)
            // 두 조각을 한 노드로 — 안 그러면 "Stash", "Map" 이 끊겨 읽힌다
            .semantics(mergeDescendants = true) {
                contentDescription = Wordmark
                heading()
            }
            .padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StashText(text = WordmarkPrefix, role = StashTextRole.H1)
        StashText(text = WordmarkSuffix, role = StashTextRole.H1, color = colors.accent)
    }
}

private const val WordmarkPrefix = "Stash"
private const val WordmarkSuffix = "Map"
private const val Wordmark = WordmarkPrefix + WordmarkSuffix
private val HeaderMinHeight = 56.dp

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeHeaderLightPreview() {
    StashTheme(darkTheme = false) {
        HomeHeader()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeHeaderDarkPreview() {
    StashTheme(darkTheme = true) {
        HomeHeader()
    }
}
