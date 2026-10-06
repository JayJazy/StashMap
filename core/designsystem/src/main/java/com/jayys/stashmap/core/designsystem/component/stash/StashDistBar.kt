package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.style

/**
 * 방문 후 평가 3종의 비율 바 — "100명 중 몇 명이 다시 가는가"를 한 줄로
 *
 * 위시리스트([StashEvalState.WantToTry])는 아직 먹기 전이라 비율에서 뺌.
 * 세 값이 모두 0이면 빈 트랙만 그림
 *
 * @param favoriteCount 다시 먹을래요 개수
 * @param averageCount 보통이에요 개수
 * @param avoidCount 다신 안 먹을래요 개수
 * @param height 바 두께
 */
@Composable
fun StashDistBar(
    favoriteCount: Int,
    averageCount: Int,
    avoidCount: Int,
    modifier: Modifier = Modifier,
    height: Dp = 12.dp,
) {
    val colors = MaterialTheme.stashColorTokens
    // style 은 @Composable 이라 여기서 미리 풀어둬야 아래 일반 람다에서 쓸 수 있다
    val segments = listOf(
        StashEvalState.Favorite.style to favoriteCount,
        StashEvalState.Average.style to averageCount,
        StashEvalState.Avoid.style to avoidCount,
    ).filter { (_, count) -> count > 0 }

    val total = segments.sumOf { (_, count) -> count }
    val description = segments.joinToString(separator = ", ") { (style, count) ->
        "${style.label} $count"
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(StashRadius.full)
            .background(colors.surface3)
            .semantics { if (total > 0) contentDescription = description },
    ) {
        // 맞닿으면 라이트에서 Favorite|Average 경계가 1.03 이라 안 보인다 (Green600·Amber600 휘도가 거의 같음)
        segments.forEachIndexed { index, (style, count) ->
            if (index > 0) {
                Box(
                    modifier = Modifier
                        .width(SegmentGap)
                        .fillMaxHeight()
                        .background(colors.surface),
                )
            }
            Box(
                modifier = Modifier
                    .weight(count.toFloat())
                    .fillMaxHeight()
                    .background(style.solid),
            )
        }
    }
}

private val SegmentGap = 2.dp

@Composable
private fun StashDistBarShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s4),
    ) {
        StashDistBar(favoriteCount = 78, averageCount = 15, avoidCount = 7)
        StashDistBar(favoriteCount = 3, averageCount = 3, avoidCount = 3, height = 8.dp)
        StashDistBar(favoriteCount = 0, averageCount = 0, avoidCount = 0)
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashDistBarLightPreview() {
    StashTheme(darkTheme = false) {
        StashDistBarShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashDistBarDarkPreview() {
    StashTheme(darkTheme = true) {
        StashDistBarShowcase()
    }
}
