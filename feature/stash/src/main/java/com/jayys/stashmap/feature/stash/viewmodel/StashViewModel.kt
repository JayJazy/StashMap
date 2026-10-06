package com.jayys.stashmap.feature.stash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.stashmap.core.model.Evaluation
import com.jayys.stashmap.core.model.RestaurantRecord
import com.jayys.stashmap.core.model.evaluationCounts
import com.jayys.stashmap.core.model.sample.SampleRecords
import com.jayys.stashmap.core.model.sortedByNewest
import com.jayys.stashmap.feature.stash.model.StashSortOrder
import com.jayys.stashmap.feature.stash.model.StashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * 맛집 탭 ViewModel
 *
 * 아직 저장소가 없어 주입받을 의존성이 없지만, `hiltViewModel()` 로 얻으려면
 * [HiltViewModel] + `@Inject constructor` 가 있어야 한다
 */
@HiltViewModel
class StashViewModel @Inject constructor() : ViewModel() {

    // 저장소(core:data)가 붙으면 이 자리가 Repository Flow 로 바뀐다
    private val _records = MutableStateFlow(SampleRecords.records)
    private val _selectedEvaluation = MutableStateFlow<Evaluation?>(null)
    private val _sortOrder = MutableStateFlow(StashSortOrder.Newest)

    val uiState: StateFlow<StashUiState> = combine(
        _records,
        _selectedEvaluation,
        _sortOrder,
    ) { records, selectedEvaluation, sortOrder ->
        StashUiState(
            isLoading = false,
            records = records.filterBy(selectedEvaluation).sortedBy(sortOrder),
            // 그리드는 필터와 무관하게 전체를 센다
            counts = records.evaluationCounts(),
            selectedEvaluation = selectedEvaluation,
            sortOrder = sortOrder,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StashUiState(),
    )

    /** 그리드 칸 클릭 — 같은 칸을 다시 누르면 필터 해제(전체) */
    fun onEvaluationFilterClick(evaluation: Evaluation) {
        _selectedEvaluation.update { selected -> if (selected == evaluation) null else evaluation }
    }
}

private fun List<RestaurantRecord>.filterBy(evaluation: Evaluation?): List<RestaurantRecord> =
    if (evaluation == null) this else filter { it.evaluation == evaluation }

private fun List<RestaurantRecord>.sortedBy(order: StashSortOrder): List<RestaurantRecord> =
    when (order) {
        StashSortOrder.Newest -> sortedByNewest()
    }
