package com.jayys.stashmap.core.data.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceStorage
import javax.inject.Inject

class SharedPreferenceStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceStorage {

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    override fun getInt(key: String): Int? {
        return if (sharedPreferences.contains(key)) sharedPreferences.getInt(key, -1) else null
    }

    override fun putInt(key: String, value: Int) {
        sharedPreferences.edit { putInt(key, value) }
    }

    override fun getBoolean(key: String): Boolean? {
        return if (sharedPreferences.contains(key)) sharedPreferences.getBoolean(key, false) else null
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    override fun getLong(key: String): Long? {
        return if (sharedPreferences.contains(key)) sharedPreferences.getLong(key, -1L) else null
    }

    override fun putLong(key: String, value: Long) {
        sharedPreferences.edit { putLong(key, value) }
    }

    override fun remove(key: String) {
        sharedPreferences.edit { remove(key) }
    }

    override fun clear() {
        sharedPreferences.edit { clear() }
    }

    override fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }
}