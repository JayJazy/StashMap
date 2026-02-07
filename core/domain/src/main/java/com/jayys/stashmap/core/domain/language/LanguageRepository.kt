package com.jayys.stashmap.core.domain.language

import com.jayys.stashmap.core.model.StashMapLanguage

interface LanguageRepository {

    fun getSavedLanguage(): StashMapLanguage

    fun saveLanguage(language: StashMapLanguage)
}