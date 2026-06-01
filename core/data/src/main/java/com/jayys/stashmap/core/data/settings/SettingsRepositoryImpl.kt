package com.jayys.stashmap.core.data.settings

import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.domain.sharedpreferences.PreferenceStorage
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys
import com.jayys.stashmap.core.model.StashMapLanguage
import java.util.Locale
import javax.inject.Inject

/**
 * [SettingsRepository]의 구현. [PreferenceStorage]를 사용해 키/타입 매핑을 수행한다.
 */
class SettingsRepositoryImpl @Inject constructor(
    private val storage: PreferenceStorage
) : SettingsRepository {

    override fun getDarkMode(): Boolean =
        storage.getBoolean(SharedPreferenceKeys.KEY_THEME_MODE) ?: false

    override fun setDarkMode(isDark: Boolean) {
        storage.putBoolean(SharedPreferenceKeys.KEY_THEME_MODE, isDark)
    }

    override fun getLanguage(): StashMapLanguage {
        val languageCode = storage.getString(SharedPreferenceKeys.KEY_LANGUAGE)
        return if (languageCode.isNotEmpty()) {
            StashMapLanguage.fromCode(languageCode) ?: systemDefault()
        } else {
            systemDefault()
        }
    }

    override fun setLanguage(language: StashMapLanguage) {
        storage.putString(SharedPreferenceKeys.KEY_LANGUAGE, language.code)
    }

    private fun systemDefault(): StashMapLanguage =
        StashMapLanguage.getSystemDefault(Locale.getDefault().language)
}
