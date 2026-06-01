package com.jayys.stashmap.feature.theme.viewmodel

import com.jayys.stashmap.base.BaseViewModel
import com.jayys.stashmap.core.common.local.AppSettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val appSettingsManager: AppSettingsManager
) : BaseViewModel() {
    val isDarkMode = appSettingsManager.isDarkMode

    fun selectTheme(isDark: Boolean) {
        appSettingsManager.setDarkMode(isDark)
    }
}