package com.jayys.stashmap.core.data.settings

import com.jayys.stashmap.core.domain.settings.SettingsRepository
import com.jayys.stashmap.core.domain.sharedpreferences.PreferenceStorage
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys
import com.jayys.stashmap.core.model.StashMapLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

/**
 * [SettingsRepository] 구현
 *
 * - 저장된 값을 메모리 상태로 보유하는 단일 진실 공급원
 * - 영속화는 [PreferenceStorage], 키/타입 매핑은 이 클래스
 * - 저장과 상태 갱신을 한 곳에서만 수행 → 이중 쓰기(split-brain) 없음
 * - 싱글톤 주입 필수 — 아니면 상태가 앱 전역에서 공유되지 않음
 */
class SettingsRepositoryImpl @Inject constructor(
    private val storage: PreferenceStorage
) : SettingsRepository {

    private val _darkMode = MutableStateFlow(readDarkMode())
    override val darkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    private val _language = MutableStateFlow(readLanguage())
    override val language: StateFlow<StashMapLanguage> = _language.asStateFlow()

    override suspend fun setDarkMode(isDark: Boolean) {
        withContext(Dispatchers.IO) {
            storage.putBoolean(SharedPreferenceKeys.KEY_THEME_MODE, isDark)
        }
        _darkMode.value = isDark
    }

    override suspend fun setLanguage(language: StashMapLanguage) {
        withContext(Dispatchers.IO) {
            storage.putString(SharedPreferenceKeys.KEY_LANGUAGE, language.code)
        }
        _language.value = language
    }

    private fun readDarkMode(): Boolean =
        storage.getBoolean(SharedPreferenceKeys.KEY_THEME_MODE) ?: false

    private fun readLanguage(): StashMapLanguage {
        val languageCode = storage.getString(SharedPreferenceKeys.KEY_LANGUAGE)
        return if (languageCode.isNotEmpty()) {
            StashMapLanguage.fromCode(languageCode) ?: systemDefault()
        } else {
            systemDefault()
        }
    }

    private fun systemDefault(): StashMapLanguage =
        StashMapLanguage.getSystemDefault(Locale.getDefault().language)
}
