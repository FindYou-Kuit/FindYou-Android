package com.example.findu.di

import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.BreedRepository
import com.example.findu.domain.repository.DetailSearchRepository
import com.example.findu.domain.repository.HomeRepository
import com.example.findu.domain.repository.InformationRepository
import com.example.findu.domain.repository.InterestRepository
import com.example.findu.domain.repository.MyRepository
import com.example.findu.domain.repository.SearchRepository
import com.example.findu.domain.repository.TokenRepository
import com.example.findu.domain.repository.UserInfoRepository
import com.example.findu.domain.repository.report.ReportRepository
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.GetBreedValidationUseCase
import com.example.findu.domain.usecase.GetDetailSearchUseCase
import com.example.findu.domain.usecase.GetNicknameUseCase
import com.example.findu.domain.usecase.GetSearchUseCase
import com.example.findu.domain.usecase.PostAiDetectionUseCase
import com.example.findu.domain.usecase.SetNicknameUseCase
import com.example.findu.domain.usecase.auth.PostCheckNicknameUseCase
import com.example.findu.domain.usecase.auth.PostGuestLoginUseCase
import com.example.findu.domain.usecase.auth.PostLoginUseCase
import com.example.findu.domain.usecase.auth.PostSignupUseCase
import com.example.findu.domain.usecase.extra.GetCentersUseCase
import com.example.findu.domain.usecase.extra.GetDepartmentsUseCase
import com.example.findu.domain.usecase.extra.GetSidoUseCase
import com.example.findu.domain.usecase.extra.GetSigunguUseCase
import com.example.findu.domain.usecase.extra.GetVolunteersUseCase
import com.example.findu.domain.usecase.home.GetHomeUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestReportAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestReportAnimalUseCase
import com.example.findu.domain.usecase.my.DeleteUserUseCase
import com.example.findu.domain.usecase.my.GetInterestUseCase
import com.example.findu.domain.usecase.my.GetNickNameUseCase
import com.example.findu.domain.usecase.my.GetReportHistoryUseCase
import com.example.findu.domain.usecase.my.GetViewedAnimalUseCase
import com.example.findu.domain.usecase.my.PatchNickNameUseCase
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
import com.example.findu.domain.usecase.report.DeleteReportUseCase
import com.example.findu.domain.usecase.report.GetAddressUseCase
import com.example.findu.domain.usecase.report.GetLatLngUseCase
import com.example.findu.domain.usecase.report.PostMissingReportUseCase
import com.example.findu.domain.usecase.report.PostWitnessReportUseCase
import com.example.findu.domain.usecase.report.UploadImagesUseCase
import com.example.findu.domain.usecase.token.ClearTokenUseCase
import com.example.findu.domain.usecase.token.GetAccessTokenUseCase
import com.example.findu.domain.usecase.token.GetRefreshTokenUseCase
import com.example.findu.domain.usecase.token.SetAccessTokenUseCase
import com.example.findu.domain.usecase.token.SetRefreshTokenUseCase
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
    fun provideGetLatLngUseCase(
        reportRepository: ReportRepository,
    ): GetLatLngUseCase = GetLatLngUseCase(reportRepository)

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

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(
        myRepository: MyRepository
    ): DeleteUserUseCase = DeleteUserUseCase(myRepository)

    @Provides
    @Singleton
    fun providePatchNickNameUseCase(
        myRepository: MyRepository
    ): PatchNickNameUseCase = PatchNickNameUseCase(myRepository)

    @Provides
    @Singleton
    fun provideGetNickNameUseCase(
        myRepository: MyRepository
    ): GetNickNameUseCase = GetNickNameUseCase(myRepository)

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

    @Provides
    @Singleton
    fun provideDeleteInterestReportAnimalUseCase(
        interestRepository: InterestRepository
    ): DeleteInterestReportAnimalUseCase = DeleteInterestReportAnimalUseCase(interestRepository)

    @Provides
    @Singleton
    fun provideDeleteInterestProtectingAnimalUseCase(
        interestRepository: InterestRepository
    ): DeleteInterestProtectingAnimalUseCase =
        DeleteInterestProtectingAnimalUseCase(interestRepository)

    @Provides
    @Singleton
    fun providePostLoginUseCase(
        authRepository: AuthRepository,
        userInfoRepository: UserInfoRepository
    ): PostLoginUseCase = PostLoginUseCase(authRepository = authRepository, userInfoRepository = userInfoRepository)

    @Provides
    @Singleton
    fun providePostGuestLoginUseCase(
        authRepository: AuthRepository,
        userInfoRepository: UserInfoRepository
    ): PostGuestLoginUseCase =
        PostGuestLoginUseCase(authRepository = authRepository, userInfoRepository = userInfoRepository)

    @Provides
    @Singleton
    fun providePostCheckEmailUseCase(
        authRepository: AuthRepository
    ): PostCheckNicknameUseCase = PostCheckNicknameUseCase(authRepository)

    @Provides
    @Singleton
    fun providePostSignupUseCase(
        authRepository: AuthRepository,
        userInfoRepository: UserInfoRepository
    ): PostSignupUseCase = PostSignupUseCase(authRepository = authRepository, userInfoRepository = userInfoRepository)

    @Provides
    @Singleton
    fun provideDeleteReportUseCase(
        reportRepository: ReportRepository
    ): DeleteReportUseCase = DeleteReportUseCase(reportRepository)

    @Provides
    @Singleton
    fun provideSetAccessTokenUseCase(
        tokenRepository: TokenRepository
    ): SetAccessTokenUseCase = SetAccessTokenUseCase(tokenRepository)

    @Provides
    @Singleton
    fun provideSetRefreshTokenUseCase(
        tokenRepository: TokenRepository
    ): SetRefreshTokenUseCase = SetRefreshTokenUseCase(tokenRepository)

    @Provides
    @Singleton
    fun provideGetAccessTokenUseCase(
        tokenRepository: TokenRepository
    ): GetAccessTokenUseCase = GetAccessTokenUseCase(tokenRepository)

    @Provides
    @Singleton
    fun provideGetRefreshTokenUseCase(
        tokenRepository: TokenRepository
    ): GetRefreshTokenUseCase = GetRefreshTokenUseCase(tokenRepository)

    @Provides
    @Singleton
    fun provideClearTokenUseCase(
        tokenRepository: TokenRepository
    ): ClearTokenUseCase = ClearTokenUseCase(tokenRepository)


    @Provides
    @Singleton
    fun provideGetDepartmentsUseCase(
        informationRepository: InformationRepository
    ): GetDepartmentsUseCase = GetDepartmentsUseCase(informationRepository)

    @Provides
    @Singleton
    fun provideGetVolunteersUseCase(
        informationRepository: InformationRepository
    ): GetVolunteersUseCase = GetVolunteersUseCase(informationRepository)

    @Provides
    @Singleton
    fun provideGetCentersUseCase(
        informationRepository: InformationRepository
    ): GetCentersUseCase = GetCentersUseCase(informationRepository)

    @Provides
    @Singleton
    fun provideGetLocalNicknameUseCase(
        userInfoRepository: UserInfoRepository
    ): GetNicknameUseCase = GetNicknameUseCase(userInfoRepository)

    @Provides
    @Singleton
    fun provideSetNicknameUseCase(
        userInfoRepository: UserInfoRepository
    ): SetNicknameUseCase = SetNicknameUseCase(userInfoRepository)

    @Provides
    @Singleton
    fun providePostAiDetectBreedUseCase(
        breedRepository: BreedRepository,
    ): PostAiDetectionUseCase = PostAiDetectionUseCase(breedRepository)

    @Provides
    @Singleton
    fun provideGetSidoUseCase(
        informationRepository: InformationRepository
    ): GetSidoUseCase = GetSidoUseCase(informationRepository)

    @Provides
    @Singleton
    fun provideGetSigunguUseCase(
        informationRepository: InformationRepository
    ): GetSigunguUseCase = GetSigunguUseCase(informationRepository)
}