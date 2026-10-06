package com.jayys.stashmap.feature.stash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.style
import com.jayys.stashmap.core.designsystem.theme.stash.toStashEvalState
import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.feature.stash.R
import com.jayys.stashmap.feature.stash.model.StashSortOrder

/**
 * 지금 걸린 필터 + 결과 개수 + 현재 정렬을 알려주는 한 줄
 *
 * 오른쪽 ▾ 는 "현재 정렬 표시"일 뿐이라 클릭도 Role 도 없다 — 정렬 선택은 다음 단계
 *
 * @param selectedEvaluation 걸린 평가 필터 (null 이면 전체)
 * @param count 지금 보이는 기록 수
 * @param sortOrder 현재 정렬
 */
@Composable
fun FilterSummaryRow(
    selectedEvaluation: Evaluation?,
    count: Int,
    sortOrder: StashSortOrder,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens

    val label = if (selectedEvaluation != null) {
        selectedEvaluation.toStashEvalState().style.label
    } else {
        stringResource(R.string.stash_filter_all)
    }
    val sortLabel = when (sortOrder) {
        StashSortOrder.Newest -> stringResource(R.string.stash_sort_newest)
    }
    val sortDescription = stringResource(R.string.stash_sort_current_description, sortLabel)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StashText(text = label, role = StashTextRole.LabelStrong, color = colors.fg)
            StashText(
                text = pluralStringResource(R.plurals.stash_filter_count, count, count),
                role = StashTextRole.BodySm,
                color = colors.fgMuted,
            )
        }

        Row(
            // "최신순"과 ▾ 가 끊겨 읽히지 않게 한 노드로 묶는다
            modifier = Modifier.semantics(mergeDescendants = true) {
                contentDescription = sortDescription
            },
            horizontalArrangement = Arrangement.spacedBy(StashSpacing.s1),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StashText(text = sortLabel, role = StashTextRole.BodySm, color = colors.fgMuted)
            Icon(
                imageVector = StashIcons.ChevronDown,
                contentDescription = null,
                modifier = Modifier.size(StashIconSize.xs),
                tint = colors.fgMuted,
            )
        }
    }
}

@Composable
private fun FilterSummaryRowPreviewContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s4),
    ) {
        FilterSummaryRow(
            selectedEvaluation = null,
            count = 9,
            sortOrder = StashSortOrder.Newest,
        )
        FilterSummaryRow(
            selectedEvaluation = Evaluation.Favorite,
            count = 3,
            sortOrder = StashSortOrder.Newest,
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun FilterSummaryRowLightPreview() {
    StashTheme(darkTheme = false) {
        FilterSummaryRowPreviewContent()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun FilterSummaryRowDarkPreview() {
    StashTheme(darkTheme = true) {
        FilterSummaryRowPreviewContent()
    }
}
