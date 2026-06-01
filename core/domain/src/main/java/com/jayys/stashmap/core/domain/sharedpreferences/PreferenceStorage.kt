package com.jayys.stashmap.core.domain.sharedpreferences

/**
 * Key-Value 기반 로컬 설정 저장소 추상화.
 *
 * 구현 기술(SharedPreferences/DataStore 등)을 노출하지 않는 저수준 저장소 인터페이스이며,
 * 도메인 계층은 이 추상화에만 의존한다.
 */
interface PreferenceStorage {
    fun getString(key: String): String
    fun putString(key: String, value: String)
    fun getInt(key: String): Int?
    fun putInt(key: String, value: Int)
    fun getBoolean(key: String): Boolean?
    fun putBoolean(key: String, value: Boolean)
    fun getLong(key: String): Long?
    fun putLong(key: String, value: Long)
    fun remove(key: String)
    fun clear()
    fun contains(key: String): Boolean
}
