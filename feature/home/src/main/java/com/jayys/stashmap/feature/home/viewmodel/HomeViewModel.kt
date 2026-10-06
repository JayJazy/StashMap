package com.jayys.stashmap.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import com.jayys.stashmap.core.model.sortedByNewest
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
            // 정렬 없이 take 하면 "최근"이 거짓말이 된다 — 맛집 탭과 같은 비교자를 쓴다
            recentRecords = records.filterNot { it.evaluation == Evaluation.WantToTry }
                .sortedByNewest()
                .take(RECENT_RECORD_LIMIT),
            wishlistRecords = records.filter { it.evaluation == Evaluation.WantToTry }
                .take(WISHLIST_LIMIT),
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
