package com.jayys.stashmap.feature.profile.viewmodel

import com.jayys.stashmap.base.BaseViewModel
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): BaseViewModel() {

    val selectedLanguage = settingsRepository.language
}
