package com.kuit.findu.di

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kuit.findu.BuildConfig
import com.kuit.findu.BuildConfig.DEBUG
import com.kuit.findu.data.datalocal.datasource.TokenLocalDataSource
import com.kuit.findu.data.dataremote.util.AuthInterceptor
import com.kuit.findu.data.dataremote.util.ErrorTrackingInterceptor
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
        errorTrackingInterceptor: ErrorTrackingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            if (DEBUG) addInterceptor(loggingInterceptor)
            else addInterceptor(errorTrackingInterceptor)
            addInterceptor(authInterceptor)
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
    fun provideErrorTrackingInterceptor(
        firebaseCrashlytics: FirebaseCrashlytics,
    ): ErrorTrackingInterceptor {
        return ErrorTrackingInterceptor(firebaseCrashlytics)
    }

    @ExperimentalSerializationApi
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
}
