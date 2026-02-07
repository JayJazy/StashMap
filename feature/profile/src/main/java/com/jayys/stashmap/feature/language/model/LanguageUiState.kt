package com.jayys.stashmap.feature.language.model

import com.jayys.stashmap.core.model.StashMapLanguage
import java.util.Locale

data class LanguageUiState(
    val searchQuery: String = "",
    val selectedLanguage: StashMapLanguage = StashMapLanguage.getSystemDefault(Locale.getDefault().language),
    val availableLanguages: List<StashMapLanguage> = StashMapLanguage.entries
)