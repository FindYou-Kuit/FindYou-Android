package com.example.findu.di

import com.example.findu.data.dataremote.datasource.local.DummyLocalDataSource
import com.example.findu.data.dataremote.datasource.remote.DummyRemoteDataSource
import com.example.findu.data.dataremote.datasourceimpl.local.DummyLocalDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.remote.DummyRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsDummyRemoteDataSource(dummyRemoteDataSourceImpl: DummyRemoteDataSourceImpl): DummyRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsDummyLocalDataSource(dummyLocalDataSourceImpl: DummyLocalDataSourceImpl): DummyLocalDataSource
}