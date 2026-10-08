package com.jayys.stashmap.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.RestaurantRecord
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import com.jayys.stashmap.core.model.sortedByNewest
import com.jayys.stashmap.feature.home.model.HomeRecordCard
import com.jayys.stashmap.feature.home.model.HomeUiState
import com.jayys.stashmap.feature.home.model.SampleMonthlySummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * 홈 대시보드 ViewModel
 *
 * 아직 저장소가 없어 주입받을 의존성이 없지만, `hiltViewModel()` 로 얻으려면
 * [HiltViewModel] + `@Inject constructor` 가 있어야 한다
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _records = MutableStateFlow(SampleRecords.records)
    private val _monthlySummary = MutableStateFlow(SampleMonthlySummary)

    // 최근 기록 / 위시리스트 / 통계는 따로 들고 있지 않고 기록 목록에서 매번 파생
    val uiState: StateFlow<HomeUiState> = combine(
        _records,
        _monthlySummary,
    ) { records, monthlySummary ->
        HomeUiState(
            // 정렬 없이 take 하면 "최근"이 거짓말이 된다 — 맛집 탭과 같은 비교자를 쓴다.
            // 매핑은 take 뒤 — 버릴 기록까지 포맷할 이유가 없다
            recentRecords = records.filterNot { it.evaluation == Evaluation.WantToTry }
                .sortedByNewest()
                .take(RECENT_RECORD_LIMIT)
                .map { it.toCard() },
            wishlistRecords = records.filter { it.evaluation == Evaluation.WantToTry }
                .take(WISHLIST_LIMIT)
                .map { it.toCard() },
            stats = records.evaluationCounts(),
            monthlySummary = monthlySummary,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(),
    )

    private companion object {
        const val RECENT_RECORD_LIMIT = 3
        const val WISHLIST_LIMIT = 1
    }
}

// internal — Preview 도 이 매퍼를 타야 화면과 안 갈린다
internal fun RestaurantRecord.toCard(): HomeRecordCard = HomeRecordCard(
    id = id,
    name = name,
    evaluation = evaluation,
    visitedAt = visitedAt,
    meta = metaLine(),
    memo = memo.ifBlank { null },
)

/**
 * 카테고리 · 지역 — 빈 값은 빼고 이어 붙인다. 전부 비면 null (빈 줄 방지)
 *
 * 맛집에 같은 함수가 있다 — 포맷을 바꾸면 StashViewModel 과 양쪽 ToCardTest 까지 네 군데.
 * 방문일은 Phase 4 에서 카드 전용 자리로 (meta 에 넣으면 10자가 앞을 먹어 지역이 잘린다)
 */
private fun RestaurantRecord.metaLine(): String? =
    listOf(category, area)
        .filter { it.isNotBlank() }
        .joinToString(separator = MetaSeparator)
        .ifEmpty { null }

private const val MetaSeparator = " · "
