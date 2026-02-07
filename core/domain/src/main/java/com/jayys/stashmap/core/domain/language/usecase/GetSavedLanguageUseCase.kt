package com.jayys.stashmap.core.domain.language.usecase

import com.jayys.stashmap.core.domain.language.LanguageRepository
import com.jayys.stashmap.core.model.StashMapLanguage
import javax.inject.Inject

class GetSavedLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(): StashMapLanguage {
        return languageRepository.getSavedLanguage()
    }
}