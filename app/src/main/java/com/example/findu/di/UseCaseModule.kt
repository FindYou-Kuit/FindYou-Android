package com.example.findu.di

import com.example.findu.domain.repository.BreedRepository
import com.example.findu.domain.repository.report.ReportRepository
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
import com.example.findu.domain.repository.HomeRepository
import com.example.findu.domain.repository.MyRepository
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.GetBreedValidationUseCase
import com.example.findu.domain.usecase.GetHomeUseCase
import com.example.findu.domain.usecase.my.GetInterestUseCase
import com.example.findu.domain.usecase.my.GetReportHistoryUseCase
import com.example.findu.domain.usecase.my.GetViewedAnimalUseCase
import com.example.findu.domain.usecase.report.GetAddressUseCase
import com.example.findu.domain.usecase.report.PostMissingReportUseCase
import com.example.findu.domain.usecase.report.PostWitnessReportUseCase
import com.example.findu.domain.usecase.report.UploadImagesUseCase
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

    @Provides
    @Singleton
    fun provideGetBreedValidationUseCase(
        breedRepository: BreedRepository
    ): GetBreedValidationUseCase = GetBreedValidationUseCase(breedRepository)

    @Provides
    @Singleton
    fun provideAnalysisImageWithGptUseCase(
        reportRepository: ReportRepository
    ): AnalysisImageWithGptUseCase = AnalysisImageWithGptUseCase(reportRepository)

    @Provides
    @Singleton
    fun provideUploadImagesUseCase(
        reportRepository: ReportRepository
    ): UploadImagesUseCase = UploadImagesUseCase(reportRepository)

    @Provides
    @Singleton
    fun providePostMissingReportUseCase(
        reportRepository: ReportRepository
    ): PostMissingReportUseCase = PostMissingReportUseCase(reportRepository)

    @Provides
    @Singleton
    fun providePostWitnessReportUseCase(
        reportRepository: ReportRepository
    ): PostWitnessReportUseCase = PostWitnessReportUseCase(reportRepository)

    @Provides
    @Singleton
    fun provideGetAddressUseCase(
        reportRepository: ReportRepository
    ): GetAddressUseCase = GetAddressUseCase(reportRepository)

    @Provides
    @Singleton
    fun provideGetInterestUseCase(
        myRepository: MyRepository
    ): GetInterestUseCase = GetInterestUseCase(myRepository)

    @Provides
    @Singleton
    fun provideGetReportHistoryUseCase(
        myRepository: MyRepository
    ): GetReportHistoryUseCase = GetReportHistoryUseCase(myRepository)

    @Provides
    @Singleton
    fun provideGetViewedAnimalUseCase(
        myRepository: MyRepository
    ): GetViewedAnimalUseCase = GetViewedAnimalUseCase(myRepository)
}