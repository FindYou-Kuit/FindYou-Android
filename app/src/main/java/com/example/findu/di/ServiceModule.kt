package com.example.findu.di

import com.example.findu.data.dataremote.service.DummyService
import com.example.findu.data.dataremote.service.GptService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesService(retrofit: Retrofit): DummyService =
        retrofit.create(DummyService::class.java)

    @Provides
    @Singleton
    fun provideGptService(retrofit: Retrofit): GptService =
        retrofit.create(GptService::class.java)

}