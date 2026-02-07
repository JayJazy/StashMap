package com.jayys.stashmap.core.data.di

import com.jayys.stashmap.core.data.sharedpreferences.SharedPreferDataSourceImpl
import com.jayys.stashmap.core.domain.sharedpreferences.SharedPreferenceStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindPreferenceStorage(
        sharedPreferDataSource: SharedPreferDataSourceImpl
    ): SharedPreferenceStorage
}