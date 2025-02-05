package com.example.findu.di

import com.example.findu.data.datalocal.datasource.DummyLocalDataSource
import com.example.findu.data.dataremote.datasource.DummyRemoteDataSource
import com.example.findu.data.datalocal.datasourceimpl.DummyLocalDataSourceImpl
import com.example.findu.data.dataremote.datasource.GptRemoteDataSource
import com.example.findu.data.dataremote.datasourceimpl.DummyRemoteDataSourceImpl
import com.example.findu.data.dataremote.datasourceimpl.GptRemoteDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindsGptRemoteDataSource(gptRemoteDataSourceImpl: GptRemoteDataSourceImpl): GptRemoteDataSource
}