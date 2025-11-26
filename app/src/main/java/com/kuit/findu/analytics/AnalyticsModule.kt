package com.kuit.findu.analytics

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {
    @Binds
    @Singleton
    abstract fun bindsAnalyticsHelper(analyticsHelperImpl: AnalyticsHelperImpl): AnalyticsHelper

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(): FirebaseAnalytics {
        return Firebase.analytics
    }
}