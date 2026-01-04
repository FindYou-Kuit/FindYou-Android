package com.kuit.findu.di

import com.kuit.findu.BuildConfig
import com.kuit.findu.data.dataremote.service.WebhookService
import com.kuit.findu.data.dataremote.util.DiscordLogger
import com.kuit.findu.domain.repository.UserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LogModule {
    @Provides
    @Singleton
    fun provideDiscordLogger(
        webhookService: WebhookService,
        userInfoRepository: UserInfoRepository,
    ): DiscordLogger =
        DiscordLogger(
            webhookService,
            userInfoRepository,
            BuildConfig.DISCORD_WEBHOOK_URL,
        )
}