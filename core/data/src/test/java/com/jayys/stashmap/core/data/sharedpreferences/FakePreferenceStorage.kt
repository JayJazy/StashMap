package com.jayys.stashmap.core.data.sharedpreferences

import com.jayys.stashmap.core.domain.sharedpreferences.PreferenceStorage
import java.util.concurrent.ConcurrentHashMap

/**
 * 테스트용 인메모리 [PreferenceStorage] 구현.
 *
 * [PreferenceStorageImpl]의 계약을 그대로 흉내 낸다.
 * - `getString`은 값이 없으면 빈 문자열을 반환한다 (null 아님)
 * - 나머지 getter는 값이 없으면 null을 반환한다
 *
 * 쓰기는 `Dispatchers.IO`에서, 읽기는 테스트 스레드에서 일어나므로
 * 스레드 간 가시성을 보장하기 위해 [ConcurrentHashMap]을 사용한다.
 */
class FakePreferenceStorage(
    initialValues: Map<String, Any> = emptyMap()
) : PreferenceStorage {

    private val values = ConcurrentHashMap<String, Any>(initialValues)

    /** 테스트에서 "실제로 저장됐는지"를 단언하기 위한 스냅샷. */
    val savedValues: Map<String, Any> get() = values.toMap()

    override fun getString(key: String): String = values[key] as? String ?: ""

    override fun putString(key: String, value: String) {
        values[key] = value
    }

    override fun getInt(key: String): Int? = values[key] as? Int

    override fun putInt(key: String, value: Int) {
        values[key] = value
    }

    override fun getBoolean(key: String): Boolean? = values[key] as? Boolean

    override fun putBoolean(key: String, value: Boolean) {
        values[key] = value
    }

    override fun getLong(key: String): Long? = values[key] as? Long

    override fun putLong(key: String, value: Long) {
        values[key] = value
    }

    override fun remove(key: String) {
        values.remove(key)
    }

    override fun clear() {
        values.clear()
    }

    override fun contains(key: String): Boolean = values.containsKey(key)
}
