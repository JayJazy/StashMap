package com.jayys.stashmap.locale

import android.content.Context
import android.content.res.Configuration
import com.jayys.stashmap.core.model.StashMapLanguage
import java.util.Locale

/** Locale 적용 헬퍼 — Configuration/Context 조작이라 도메인 계층으로 분리 불가 */
object LocaleHelper {

    /**
     * 언어를 적용한 새 [Context] 반환
     *
     * - `attachBaseContext`에서 리소스 로딩 전에 호출해야 함
     * - `Locale.setDefault`로 프로세스 전역 기본 Locale도 바꿈 (부수효과)
     */
    fun wrap(context: Context, language: StashMapLanguage): Context {
        val locale = Locale.forLanguageTag(language.code)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }
}
