package com.jayys.stashmap.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.component.stash.StashCard
import com.jayys.stashmap.core.designsystem.component.stash.StashEvalChipSize
import com.jayys.stashmap.core.designsystem.component.stash.StashEvalIconChip
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.layout.SpacerHeight
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.model.EvaluationCounts
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords

/**
 * 평가 4상태 개수를 가로 한 줄로 보여주는 통계 줄
 *
 * 맛집 탭의 2×2 그리드와 달리 라벨 텍스트가 없다 — 칩 자체가 상태를 말해야 한다
 */
@Composable
fun HomeEvalStatRow(
    stats: EvaluationCounts,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
    ) {
        StatCell(
            state = StashEvalState.Favorite,
            count = stats.favorite,
            modifier = Modifier.weight(1f),
        )
        StatCell(
            state = StashEvalState.Average,
            count = stats.average,
            modifier = Modifier.weight(1f),
        )
        StatCell(
            state = StashEvalState.Avoid,
            count = stats.avoid,
            modifier = Modifier.weight(1f),
        )
        StatCell(
            state = StashEvalState.WantToTry,
            count = stats.wantToTry,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun StatCell(
    state: StashEvalState,
    count: Int,
    modifier: Modifier = Modifier,
) {
    // 칸이 좁아 카드 좌우 여백만 줄인다 (반경·그림자는 같은 화면의 가게 카드와 맞춰 DS 기본값 유지)
    StashCard(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = StashSpacing.s3, horizontal = StashSpacing.s1),
    ) {
        // 옆에 라벨 텍스트가 없는 자리 → contentDescription 기본값(상태 라벨)을 그대로 둬야 TalkBack 이 상태를 읽는다
        StashEvalIconChip(
            state = state,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            size = StashEvalChipSize.Md,
        )

        SpacerHeight(StashSpacing.s2)

        StashText(
            text = count.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            role = StashTextRole.H1,
        )
    }
}

@Composable
private fun HomeEvalStatRowPreviewContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        HomeEvalStatRow(
            stats = SampleRecords.records.evaluationCounts(),
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeEvalStatRowLightPreview() {
    StashTheme(darkTheme = false) {
        HomeEvalStatRowPreviewContent()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeEvalStatRowDarkPreview() {
    StashTheme(darkTheme = true) {
        HomeEvalStatRowPreviewContent()
    }
}
