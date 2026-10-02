package com.jayys.stashmap.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.jayys.stashmap.core.domain.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    settingsRepository: SettingsRepository
): ViewModel() {

    val selectedLanguage = settingsRepository.language
}
