package com.jayys.stashmap.feature.profile.model

import com.jayys.stashmap.core.model.StashMapLanguage
import java.util.Locale

data class ProfileUiState(
    val selectedLanguage: StashMapLanguage = StashMapLanguage.getSystemDefault(Locale.getDefault().language)
)