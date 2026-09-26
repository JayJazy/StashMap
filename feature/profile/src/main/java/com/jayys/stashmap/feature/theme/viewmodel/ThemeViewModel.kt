package com.jayys.stashmap.feature.theme.viewmodel

import com.jayys.stashmap.base.BaseViewModel
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel() {
    val isDarkMode = settingsRepository.darkMode

    fun selectTheme(isDark: Boolean) {
        launch { settingsRepository.setDarkMode(isDark) }
    }
}
