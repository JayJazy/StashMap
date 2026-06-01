package com.jayys.stashmap.core.common.local

import android.content.Context
import android.content.res.Configuration
import com.jayys.stashmap.core.model.StashMapLanguage
import java.util.Locale

/**
 * 언어(Locale) 적용을 담당하는 **stateless 프레임워크 헬퍼**.
 *
 * `Configuration`/`Locale` 조작은 Android 프레임워크와 `Context`에 본질적으로 묶여 있어
 * 도메인 계층으로 옮길 수 없는 영역이다. 상태를 갖지 않고 순수하게 변환만 수행한다.
 */
object LocaleHelper {

    /**
     * 주어진 언어로 Locale을 적용한 새 [Context]를 반환한다.
     *
     * `Activity.attachBaseContext`에서 리소스 로딩 전에 호출되어야 한다.
     */
    fun wrap(context: Context, language: StashMapLanguage): Context {
        val locale = Locale.forLanguageTag(language.code)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }
}
