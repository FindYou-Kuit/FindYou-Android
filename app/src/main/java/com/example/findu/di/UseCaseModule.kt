package com.example.findu.di

import com.example.findu.domain.repository.DetailSearchRepository
import com.example.findu.domain.repository.report.ReportRepository
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
import com.example.findu.domain.repository.HomeRepository
import com.example.findu.domain.repository.SearchRepository
import com.example.findu.domain.usecase.GetDetailSearchUseCase
import com.example.findu.domain.usecase.GetHomeUseCase
import com.example.findu.domain.usecase.GetSearchUseCase
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
    fun provideGetSearchUseCase(
        searchRepository: SearchRepository
    ): GetSearchUseCase = GetSearchUseCase(searchRepository)

    @Provides
    @Singleton
    fun provideGetDetailSearchUseCase(
        detailSearchRepository: DetailSearchRepository
    ): GetDetailSearchUseCase = GetDetailSearchUseCase(detailSearchRepository)

    @Provides
    @Singleton
    fun provideAnalysisImageWithGptUseCase(
        reportRepository: ReportRepository
    ): AnalysisImageWithGptUseCase = AnalysisImageWithGptUseCase(reportRepository)
}