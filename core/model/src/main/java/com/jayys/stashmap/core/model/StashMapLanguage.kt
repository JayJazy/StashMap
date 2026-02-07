package com.jayys.stashmap.core.model

enum class StashMapLanguage(
    val displayName: String,
    val code: String,
    val flag: String
) {
    KOREAN(
        displayName = "한국어",
        code = "ko",
        flag = "🇰🇷"
    ),
    ENGLISH(
        displayName = "English",
        code = "en",
        flag = "🇬🇧"
    );

    companion object {
        fun fromCode(code: String): StashMapLanguage? {
            return entries.find { it.code == code }
        }

        fun getSystemDefault(systemLanguageCode: String): StashMapLanguage {
            return fromCode(systemLanguageCode) ?: ENGLISH
        }
    }
}