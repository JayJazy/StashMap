package com.jayys.stashmap.feature.profile.viewmodel

import com.jayys.stashmap.base.BaseViewModel
import com.jayys.stashmap.core.common.local.AppSettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    appSettingsManager: AppSettingsManager
): BaseViewModel() {

    val selectedLanguage = appSettingsManager.stashLanguage
}