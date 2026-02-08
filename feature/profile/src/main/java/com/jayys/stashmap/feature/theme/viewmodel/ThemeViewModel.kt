package com.jayys.stashmap.feature.theme.viewmodel

import com.jayys.stashmap.core.common.local.LocalManager
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceStorage
import com.jayys.stashmap.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : BaseViewModel() {
    val isDarkMode = LocalManager.isDarkMode

    fun selectTheme(isDark: Boolean) {
        sharedPreferenceStorage.putBoolean(SharedPreferenceKeys.KEY_THEME_MODE, isDark)
        LocalManager.applyTheme(isDark)
    }
}