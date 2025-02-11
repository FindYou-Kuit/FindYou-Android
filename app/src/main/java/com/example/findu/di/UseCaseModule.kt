package com.example.findu.di

import com.example.findu.domain.repository.BreedRepository
import com.example.findu.domain.repository.HomeRepository
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.GetHomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetHomeUseCase(
        homeRepository: HomeRepository
    ): GetHomeUseCase = GetHomeUseCase(homeRepository)

    @Provides
    @Singleton
    fun provideGetBreedDataUseCase(
        breedRepository: BreedRepository
    ): GetBreedDataUseCase = GetBreedDataUseCase(breedRepository)
}