package com.example.findu.di

import com.example.findu.domain.repository.DetailSearchRepository
import com.example.findu.domain.repository.BreedRepository
import com.example.findu.domain.repository.report.ReportRepository
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
import com.example.findu.domain.repository.HomeRepository
import com.example.findu.domain.repository.InterestRepository
import com.example.findu.domain.repository.SearchRepository
import com.example.findu.domain.usecase.GetDetailSearchUseCase
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.GetBreedValidationUseCase
import com.example.findu.domain.usecase.GetHomeUseCase
import com.example.findu.domain.usecase.GetSearchUseCase
import com.example.findu.domain.usecase.interest.PostInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestReportAnimalUseCase
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
    fun providePostInterestReportAnimalUseCase(
        interestRepository: InterestRepository
    ): PostInterestReportAnimalUseCase = PostInterestReportAnimalUseCase(interestRepository)

    @Provides
    @Singleton
    fun providePostInterestProtectingAnimalUseCase(
        interestRepository: InterestRepository
    ): PostInterestProtectingAnimalUseCase = PostInterestProtectingAnimalUseCase(interestRepository)
}