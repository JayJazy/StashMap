package com.jayys.stashmap.core.common.language

import android.content.Context
import android.content.res.Configuration
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys
import com.jayys.stashmap.core.model.StashMapLanguage
import java.util.Locale

object LanguageHelper {

    private const val PREFERENCE_NAME = "STASH_MAP_PREFERENCE_APP_KEY"

    fun applyLanguage(context: Context): Context {
        val language = getSavedLanguage(context)
        return updateLocale(context, language)
    }

    fun getSavedLanguage(context: Context): StashMapLanguage {
        val sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString(SharedPreferenceKeys.KEY_LANGUAGE, null)

        return if (languageCode != null) {
            StashMapLanguage.fromCode(languageCode) ?: getSystemLanguage()
        } else {
            getSystemLanguage()
        }
    }

    private fun getSystemLanguage(): StashMapLanguage {
        val systemLanguageCode = Locale.getDefault().language
        return StashMapLanguage.getSystemDefault(systemLanguageCode)
    }

    private fun updateLocale(context: Context, language: StashMapLanguage): Context {
        val locale = Locale(language.code)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }
}