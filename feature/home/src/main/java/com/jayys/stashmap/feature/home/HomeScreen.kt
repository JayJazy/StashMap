package com.jayys.stashmap.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayys.stashmap.core.designsystem.component.stash.StashFab
import com.jayys.stashmap.core.designsystem.component.stash.StashRestaurantCard
import com.jayys.stashmap.core.designsystem.layout.SpacerHeight
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.toStashEvalState
import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.RestaurantRecord
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import com.jayys.stashmap.core.model.sortedByNewest
import com.jayys.stashmap.feature.home.model.HomeUiState
import com.jayys.stashmap.feature.home.model.SampleMonthlySummary
import com.jayys.stashmap.feature.home.ui.HomeEvalStatRow
import com.jayys.stashmap.feature.home.ui.HomeGreeting
import com.jayys.stashmap.feature.home.ui.HomeHeader
import com.jayys.stashmap.feature.home.ui.HomeQuickRecordCta
import com.jayys.stashmap.feature.home.ui.HomeSectionLabel
import com.jayys.stashmap.feature.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onQuickRecordClick: () -> Unit,
    onRecordClick: (String) -> Unit,
    onSeeAllRecentClick: () -> Unit,
    onSeeAllWishlistClick: () -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onQuickRecordClick = onQuickRecordClick,
        onRecordClick = onRecordClick,
        onSeeAllRecentClick = onSeeAllRecentClick,
        onSeeAllWishlistClick = onSeeAllWishlistClick,
        onFabClick = onFabClick,
        modifier = modifier,
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onQuickRecordClick: () -> Unit,
    onRecordClick: (String) -> Unit,
    onSeeAllRecentClick: () -> Unit,
    onSeeAllWishlistClick: () -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.stashColorTokens.bg),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeHeader()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = StashSpacing.s4)
                    // FAB 가 마지막 카드를 덮지 않도록 FAB 높이 + 여백만큼 비워 둔다
                    .padding(bottom = ScrollBottomPadding),
            ) {
                SpacerHeight(StashSpacing.s4)

                HomeGreeting(summary = uiState.monthlySummary)

                SpacerHeight(StashSpacing.s5)

                HomeEvalStatRow(stats = uiState.stats)

                SpacerHeight(StashSpacing.s4)

                HomeQuickRecordCta(onClick = onQuickRecordClick)

                // 비어 있는 섹션은 제목째 숨긴다
                if (uiState.recentRecords.isNotEmpty()) {
                    RecordSection(
                        title = stringResource(R.string.home_section_recent),
                        records = uiState.recentRecords,
                        onSeeAllClick = onSeeAllRecentClick,
                        onRecordClick = onRecordClick,
                    )
                }

                if (uiState.wishlistRecords.isNotEmpty()) {
                    RecordSection(
                        title = stringResource(R.string.home_section_wishlist),
                        records = uiState.wishlistRecords,
                        onSeeAllClick = onSeeAllWishlistClick,
                        onRecordClick = onRecordClick,
                    )
                }
            }
        }

        StashFab(
            onClick = onFabClick,
            contentDescription = stringResource(R.string.home_fab_description),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(StashSpacing.s4),
            label = stringResource(R.string.home_fab_label),
        )
    }
}

@Composable
private fun RecordSection(
    title: String,
    records: List<RestaurantRecord>,
    onSeeAllClick: () -> Unit,
    onRecordClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SpacerHeight(StashSpacing.s4)

        HomeSectionLabel(title = title, onSeeAllClick = onSeeAllClick)

        SpacerHeight(StashSpacing.s2)

        Column(verticalArrangement = Arrangement.spacedBy(StashSpacing.s3)) {
            records.forEach { record ->
                StashRestaurantCard(
                    name = record.name,
                    state = record.evaluation.toStashEvalState(),
                    onClick = { onRecordClick(record.id) },
                    meta = record.metaLine(),
                    memo = record.memo.ifBlank { null },
                )
            }
        }
    }
}

/** 카테고리 · 지역 · 거리 — 빈 값은 빼고 이어 붙인다. 전부 비면 null (빈 줄 방지) */
private fun RestaurantRecord.metaLine(): String? =
    listOf(category, area, distance)
        .filter { it.isNotBlank() }
        .joinToString(separator = MetaSeparator)
        .ifEmpty { null }

private const val MetaSeparator = " · "
private val ScrollBottomPadding = 88.dp

@Composable
private fun HomeContentPreviewContent() {
    HomeContent(
        uiState = HomeUiState(
            // HomeViewModel 과 같은 순서여야 한다 — 한쪽만 고치면 Preview 가 화면과 다른 카드를 띄운다
            recentRecords = SampleRecords.records
                .filterNot { it.evaluation == Evaluation.WantToTry }
                .sortedByNewest()
                .take(3),
            wishlistRecords = SampleRecords.records
                .filter { it.evaluation == Evaluation.WantToTry }
                .take(1),
            stats = SampleRecords.records.evaluationCounts(),
            monthlySummary = SampleMonthlySummary,
        ),
        onQuickRecordClick = {},
        onRecordClick = {},
        onSeeAllRecentClick = {},
        onSeeAllWishlistClick = {},
        onFabClick = {},
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeContentLightPreview() {
    StashTheme(darkTheme = false) {
        HomeContentPreviewContent()
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeContentDarkPreview() {
    StashTheme(darkTheme = true) {
        HomeContentPreviewContent()
    }
}
