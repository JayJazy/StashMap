package com.jayys.stashmap.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import com.jayys.stashmap.feature.home.model.HomeSampleData
import com.jayys.stashmap.feature.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    private val _records = MutableStateFlow(HomeSampleData.records)
    private val _stats = MutableStateFlow(HomeSampleData.stats)
    private val _monthlySummary = MutableStateFlow(HomeSampleData.monthlySummary)

    // 최근 기록 / 위시리스트는 따로 들고 있지 않고 기록 목록에서 매번 파생
    val uiState: StateFlow<HomeUiState> = combine(
        _records,
        _stats,
        _monthlySummary,
    ) { records, stats, monthlySummary ->
        HomeUiState(
            recentRecords = records.filterNot { it.evaluation == StashEvalState.WantToTry }
                .take(RECENT_RECORD_LIMIT),
            wishlistRecords = records.filter { it.evaluation == StashEvalState.WantToTry }
                .take(WISHLIST_LIMIT),
            stats = stats,
            monthlySummary = monthlySummary,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = HomeUiState(),
    )

    private companion object {
        const val RECENT_RECORD_LIMIT = 3
        const val WISHLIST_LIMIT = 1
    }
}
