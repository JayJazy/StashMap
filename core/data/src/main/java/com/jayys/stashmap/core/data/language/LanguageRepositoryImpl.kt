package com.jayys.stashmap.core.data.language

import com.jayys.stashmap.core.domain.language.LanguageRepository
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceStorage
import com.jayys.stashmap.core.model.StashMapLanguage
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepositoryImpl @Inject constructor(
    private val sharedPreferenceStorage: SharedPreferenceStorage
) : LanguageRepository {

    override fun getSavedLanguage(): StashMapLanguage {
        val languageCode = sharedPreferenceStorage.getString(SharedPreferenceKeys.KEY_LANGUAGE)

        return if (languageCode.isNotEmpty()) {
            StashMapLanguage.fromCode(languageCode) ?: getSystemLanguage()
        } else {
            getSystemLanguage()
        }
    }

    private fun getSystemLanguage(): StashMapLanguage {
        val systemLanguageCode = Locale.getDefault().language
        return StashMapLanguage.getSystemDefault(systemLanguageCode)
    }

    override fun saveLanguage(language: StashMapLanguage) {
        sharedPreferenceStorage.putString(SharedPreferenceKeys.KEY_LANGUAGE, language.code)
    }
}