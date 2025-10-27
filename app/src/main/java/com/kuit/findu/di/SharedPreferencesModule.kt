package com.kuit.findu.di

import android.content.Context
import android.content.SharedPreferences
import com.kuit.findu.di.qualifier.DeviceIdPrefs
import com.kuit.findu.di.qualifier.TokenPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @TokenPrefs
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)


    @DeviceIdPrefs
    @Provides
    @Singleton
    fun provideDeviceIdSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("device_id_prefs", Context.MODE_PRIVATE)
    }
}