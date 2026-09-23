package com.jayys.stashmap.feature.language.viewmodel

import androidx.lifecycle.viewModelScope
import com.jayys.stashmap.base.BaseViewModel
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import com.jayys.stashmap.feature.language.model.LanguageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
): BaseViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val selectedLanguage = settingsRepository.language

    val uiState: StateFlow<LanguageUiState> = combine(
        _searchQuery,
        selectedLanguage
    ) { searchQuery, selectedLanguage ->
        LanguageUiState(
            searchQuery = searchQuery,
            selectedLanguage = selectedLanguage,
            availableLanguages = filterLanguages(searchQuery)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LanguageUiState(
            selectedLanguage = selectedLanguage.value
        )
    )

    private fun filterLanguages(query: String): List<StashMapLanguage> {
        val allLanguages = StashMapLanguage.entries
        return if (query.isBlank()) {
            allLanguages
        } else {
            allLanguages.filter { language ->
                language.displayName.contains(query, ignoreCase = true) ||
                language.code.contains(query, ignoreCase = true)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun selectLanguage(language: StashMapLanguage) {
        launch { settingsRepository.setLanguage(language) }
    }
}