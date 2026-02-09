package com.jayys.stashmap.core.common.local

import android.content.Context
import android.content.res.Configuration
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys.PREFERENCE_APP_KEY
import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

object LocalManager {

    // 테마 관리
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    // 언어 관리
    private val _stashLanguage = MutableStateFlow(getSystemLanguage())
    val stashLanguage = _stashLanguage.asStateFlow()

    fun initTheme(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCE_APP_KEY, Context.MODE_PRIVATE)
        _isDarkMode.value = sharedPreferences.getBoolean(SharedPreferenceKeys.KEY_THEME_MODE, false)
    }

    fun initLanguage(context: Context) {
        _stashLanguage.value = getLanguageFromPreference(context)
    }

    fun applyTheme(isDark: Boolean) {
        _isDarkMode.value = isDark
    }

    fun applyLanguage(context: Context): Context {
        val language = getLanguageFromPreference(context)
        return updateLocale(context, language)
    }

    private fun getLanguageFromPreference(context: Context): StashMapLanguage {
        val sharedPreferences = context.getSharedPreferences(PREFERENCE_APP_KEY, Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString(SharedPreferenceKeys.KEY_LANGUAGE, null)

        return if (languageCode != null && languageCode.isNotEmpty()) {
            StashMapLanguage.fromCode(languageCode) ?: getSystemLanguage()
        } else {
            getSystemLanguage()
        }
    }

    fun setLanguage(language: StashMapLanguage) {
        _stashLanguage.value = language
    }

    private fun getSystemLanguage(): StashMapLanguage {
        val systemLanguageCode = Locale.getDefault().language
        return StashMapLanguage.getSystemDefault(systemLanguageCode)
    }

    private fun updateLocale(context: Context, language: StashMapLanguage): Context {
        val locale = Locale.forLanguageTag(language.code)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }
}