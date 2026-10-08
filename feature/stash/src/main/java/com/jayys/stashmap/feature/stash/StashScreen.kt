package com.jayys.stashmap.feature.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.stashmap.core.designsystem.component.stash.StashEmptyState
import com.jayys.stashmap.core.designsystem.component.stash.StashEvalStatGrid
import com.jayys.stashmap.core.designsystem.component.stash.StashFab
import com.jayys.stashmap.core.designsystem.component.stash.StashRestaurantCard
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.toEvaluation
import com.jayys.stashmap.core.designsystem.theme.stash.toStashEvalState
import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import com.jayys.stashmap.core.model.sortedByNewest
import com.jayys.stashmap.feature.stash.model.StashUiState
import com.jayys.stashmap.feature.stash.ui.FilterSummaryRow
import com.jayys.stashmap.feature.stash.ui.TitleHeader
import com.jayys.stashmap.feature.stash.viewmodel.StashViewModel
import com.jayys.stashmap.feature.stash.viewmodel.toCard

@Composable
fun StashScreen(
    onRecordClick: (String) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StashViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StashContent(
        uiState = uiState,
        onEvaluationFilterClick = viewModel::onEvaluationFilterClick,
        onRecordClick = onRecordClick,
        onFabClick = onFabClick,
        modifier = modifier,
    )
}

@Composable
fun StashContent(
    uiState: StashUiState,
    onEvaluationFilterClick: (Evaluation) -> Unit,
    onRecordClick: (String) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // 필터 때문에 빈 것과 기록 자체가 없는 것은 다른 화면이다
    val hasNoRecords = uiState.records.isEmpty() && uiState.selectedEvaluation == null
    val showEmptyState = !uiState.isLoading && hasNoRecords

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.stashColorTokens.bg),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TitleHeader(title = stringResource(R.string.stash_title))

            when {
                // 로딩 시각 표현은 DS 에 없어 헤더만 두고 비운다
                uiState.isLoading -> Unit

                // 가로 모드·큰 글꼴에서 CTA 가 잘려 닿지 못하는 걸 막으려면 스크롤이 필요하다
                showEmptyState -> Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    StashEmptyState(
                        title = stringResource(R.string.stash_empty_title),
                        description = stringResource(R.string.stash_empty_description),
                        icon = StashIcons.Utensils,
                        actionLabel = stringResource(R.string.stash_empty_action),
                        onAction = onFabClick,
                    )
                }

                else -> RecordList(
                    uiState = uiState,
                    onEvaluationFilterClick = onEvaluationFilterClick,
                    onRecordClick = onRecordClick,
                )
            }
        }

        // 빈 상태 화면에선 그 CTA 가 진입점 — FAB 까지 띄우면 같은 동작이 둘이 된다.
        // 로딩 중에는 띄운다(기록 추가를 막을 이유가 없다)
        if (!showEmptyState) {
            StashFab(
                onClick = onFabClick,
                contentDescription = stringResource(R.string.stash_fab_description),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(StashSpacing.s4),
                label = stringResource(R.string.stash_fab_label),
            )
        }
    }
}

@Composable
private fun RecordList(
    uiState: StashUiState,
    onEvaluationFilterClick: (Evaluation) -> Unit,
    onRecordClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = StashSpacing.s4,
            top = StashSpacing.s4,
            end = StashSpacing.s4,
            // FAB 가 마지막 카드를 덮지 않도록 FAB 높이 + 여백만큼 비워 둔다
            bottom = ListBottomPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s3),
    ) {
        item {
            StashEvalStatGrid(
                favoriteCount = uiState.counts.favorite,
                averageCount = uiState.counts.average,
                avoidCount = uiState.counts.avoid,
                wantToTryCount = uiState.counts.wantToTry,
                selectedState = uiState.selectedEvaluation?.toStashEvalState(),
                onStateClick = { state -> onEvaluationFilterClick(state.toEvaluation()) },
            )
        }

        item {
            FilterSummaryRow(
                selectedEvaluation = uiState.selectedEvaluation,
                count = uiState.records.size,
                sortOrder = uiState.sortOrder,
            )
        }

        if (uiState.records.isEmpty()) {
            // 그리드·필터 줄은 남긴다 — 없애면 필터를 풀 수단이 사라진다
            item {
                StashText(
                    text = stringResource(R.string.stash_filtered_empty_title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = StashSpacing.s10),
                    role = StashTextRole.BodySm,
                    color = MaterialTheme.stashColorTokens.fgMuted,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            // 필터가 목록을 통째로 갈아끼우므로 key 없이는 항목 재사용이 어긋난다
            items(items = uiState.records, key = { it.id }) { card ->
                StashRestaurantCard(
                    name = card.name,
                    state = card.evaluation.toStashEvalState(),
                    onClick = { onRecordClick(card.id) },
                    meta = card.meta,
                    memo = card.memo,
                )
            }
        }
    }
}

private val ListBottomPadding = 88.dp

private val PreviewLoadedState = StashUiState(
    isLoading = false,
    // StashViewModel 과 같은 순서·같은 매퍼여야 한다 — 한쪽만 고치면 Preview 가 화면과 다른 카드를 띄운다
    records = SampleRecords.records.sortedByNewest().map { it.toCard() },
    counts = SampleRecords.records.evaluationCounts(),
)

private val PreviewEmptyState = StashUiState(isLoading = false)

private val PreviewFilteredEmptyState = StashUiState(
    isLoading = false,
    records = emptyList(),
    counts = SampleRecords.records.evaluationCounts(),
    selectedEvaluation = Evaluation.Avoid,
)

@Composable
private fun StashContentPreviewContent(uiState: StashUiState) {
    StashContent(
        uiState = uiState,
        onEvaluationFilterClick = {},
        onRecordClick = {},
        onFabClick = {},
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun StashContentLightPreview() {
    StashTheme(darkTheme = false) {
        StashContentPreviewContent(PreviewLoadedState)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun StashContentDarkPreview() {
    StashTheme(darkTheme = true) {
        StashContentPreviewContent(PreviewLoadedState)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun StashContentEmptyLightPreview() {
    StashTheme(darkTheme = false) {
        StashContentPreviewContent(PreviewEmptyState)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun StashContentEmptyDarkPreview() {
    StashTheme(darkTheme = true) {
        StashContentPreviewContent(PreviewEmptyState)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun StashContentFilteredEmptyLightPreview() {
    StashTheme(darkTheme = false) {
        StashContentPreviewContent(PreviewFilteredEmptyState)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun StashContentFilteredEmptyDarkPreview() {
    StashTheme(darkTheme = true) {
        StashContentPreviewContent(PreviewFilteredEmptyState)
    }
}
