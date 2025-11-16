package com.kuit.findu.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kuit.findu.data.dataremote.service.AuthService
import com.kuit.findu.data.dataremote.service.BreedService
import com.kuit.findu.data.dataremote.service.DetailSearchService
import com.kuit.findu.data.dataremote.service.DummyService
import com.kuit.findu.data.dataremote.service.GptService
import com.kuit.findu.data.dataremote.service.HomeService
import com.kuit.findu.data.dataremote.service.InformationService
import com.kuit.findu.data.dataremote.service.InquiryService
import com.kuit.findu.data.dataremote.service.InterestService
import com.kuit.findu.data.dataremote.service.MyService
import com.kuit.findu.data.dataremote.service.NaverService
import com.kuit.findu.data.dataremote.service.ReportService
import com.kuit.findu.data.dataremote.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
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
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideDetailSearchService(retrofit: Retrofit): DetailSearchService =
        retrofit.create(DetailSearchService::class.java)

    @Provides
    @Singleton
    fun provideBreedService(retrofit: Retrofit): BreedService =
        retrofit.create(BreedService::class.java)

    @Provides
    @Singleton
    fun provideReportService(retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)

    @Provides
    @Singleton
    fun provideInterestService(retrofit: Retrofit): InterestService =
        retrofit.create(InterestService::class.java)

    @Provides
    @Singleton
    fun provideMyService(retrofit: Retrofit): MyService =
        retrofit.create(MyService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideInformationService(retrofit: Retrofit): InformationService =
        retrofit.create(InformationService::class.java)

    @Provides
    @Singleton
    fun provideGptService(
        okHttpClient: OkHttpClient,
        json: Json,
    ): GptService {
        val gptRetrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull()))
            )
            .build()

        return gptRetrofit.create(GptService::class.java)
    }

    @Provides
    @Singleton
    fun provideNaverService(
        okHttpClient: OkHttpClient,
        json: Json,
    ): NaverService {
        val naverRetrofit = Retrofit.Builder()
            .baseUrl("https://maps.apigw.ntruss.com/")
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull()))
            )
            .build()

        return naverRetrofit.create(NaverService::class.java)
    }

    @Provides
    @Singleton
    fun provideInquiryService(retrofit: Retrofit): InquiryService =
        retrofit.create(InquiryService::class.java)
}