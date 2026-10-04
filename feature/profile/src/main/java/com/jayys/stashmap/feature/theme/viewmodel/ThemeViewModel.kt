package com.jayys.stashmap.feature.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val isDarkMode = settingsRepository.darkMode

    fun selectTheme(isDark: Boolean) {
        viewModelScope.launch { settingsRepository.setDarkMode(isDark) }
    }
}
