package com.jayys.stashmap.core.domain.language.usecase

import com.jayys.stashmap.core.model.StashMapLanguage
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor() {
    operator fun invoke(): List<StashMapLanguage> {
        return StashMapLanguage.entries
    }
}