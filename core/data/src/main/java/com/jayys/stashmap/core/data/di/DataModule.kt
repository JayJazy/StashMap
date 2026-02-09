package com.jayys.stashmap.core.data.di

import android.content.Context
import android.content.SharedPreferences
import com.jayys.stashmap.core.data.sharedpreferences.SharedPreferenceStorageImpl
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceKeys.PREFERENCE_APP_KEY
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_APP_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceStorage(
        sharedPreferences: SharedPreferences
    ): SharedPreferenceStorage {
        return SharedPreferenceStorageImpl(sharedPreferences)
    }
}