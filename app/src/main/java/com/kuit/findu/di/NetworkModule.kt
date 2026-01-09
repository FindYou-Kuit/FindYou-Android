package com.kuit.findu.di

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kuit.findu.BuildConfig
import com.kuit.findu.BuildConfig.DEBUG
import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import com.kuit.findu.data.dataremote.service.ReissueService
import com.kuit.findu.data.dataremote.util.AuthAuthenticator
import com.kuit.findu.data.dataremote.util.AuthInterceptor
import com.kuit.findu.data.dataremote.util.DiscordLogger
import com.kuit.findu.data.dataremote.util.ErrorTrackingInterceptor
import com.kuit.findu.di.qualifier.ReissueRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providesJson(): Json =
        Json {
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
            explicitNulls = false
            ignoreUnknownKeys = true
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
        errorTrackingInterceptor: ErrorTrackingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            addInterceptor(authInterceptor)
            if (DEBUG) addInterceptor(loggingInterceptor)
            else addInterceptor(errorTrackingInterceptor)
            authenticator(authAuthenticator)
        }.build()

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenLocalDataSource: TokenLocalDataSource,
        @ApplicationContext context: Context,
    ): AuthInterceptor {
        return AuthInterceptor(tokenLocalDataSource, context)
    }

    @Provides
    @Singleton
    fun provideAuthAuthenticator(
        tokenLocalDataSource: TokenLocalDataSource,
        reissueService: ReissueService,
    ): AuthAuthenticator {
        return AuthAuthenticator(tokenLocalDataSource, reissueService)
    }

    @Provides
    @Singleton
    fun provideErrorTrackingInterceptor(
        firebaseCrashlytics: FirebaseCrashlytics,
        discordLogger: DiscordLogger,
    ): ErrorTrackingInterceptor {
        return ErrorTrackingInterceptor(
            firebaseCrashlytics = firebaseCrashlytics,
            discordLogger = discordLogger
        )
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull()))
            )
            .build()

    @Provides
    @ReissueRetrofit
    @Singleton
    fun providesReissueRetrofit(
        loggingInterceptor: HttpLoggingInterceptor,
        json: Json,
    ): Retrofit {
        val authOkHttpClient = OkHttpClient.Builder().apply {
            readTimeout(20, TimeUnit.SECONDS)
            if (DEBUG) addInterceptor(loggingInterceptor)
        }.build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(authOkHttpClient)
            .addConverterFactory(
                json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull()))
            )
            .build()
    }
}
