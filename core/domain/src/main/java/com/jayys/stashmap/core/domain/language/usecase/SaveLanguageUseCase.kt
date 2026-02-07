package com.jayys.stashmap.core.domain.language.usecase

import com.jayys.stashmap.core.domain.language.LanguageRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import javax.inject.Inject

class SaveLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(language: StashMapLanguage) {
        languageRepository.saveLanguage(language)
    }
}