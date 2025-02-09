package com.example.findu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
//    @Provides
//    @Singleton
//    fun provideGetHomeUseCase(
//        homeRepository: HomeRepository
//    ): GetHomeUseCase = GetHomeUseCase(homeRepository)
}