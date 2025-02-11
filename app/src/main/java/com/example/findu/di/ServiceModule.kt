package com.example.findu.di

import com.example.findu.data.dataremote.service.BreedService
import com.example.findu.data.dataremote.service.DummyService
import com.example.findu.data.dataremote.service.HomeService
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
    fun provideHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideBreedService(retrofit: Retrofit): BreedService =
        retrofit.create(BreedService::class.java)
}