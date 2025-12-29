package com.kuit.findu.di

import com.kuit.findu.data.dataremote.service.WebhookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LogNetworkModule {

    @Provides
    @Singleton
    @Named("webhook")
    fun provideWebhookOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    @Singleton
    @Named("webhook")
    fun provideWebhookRetrofit(
        @Named("webhook") okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://discord.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWebhookService(
        @Named("webhook") retrofit: Retrofit
    ): WebhookService =
        retrofit.create(WebhookService::class.java)
}