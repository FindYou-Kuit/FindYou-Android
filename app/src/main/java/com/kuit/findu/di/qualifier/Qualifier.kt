package com.kuit.findu.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenPrefs

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceIdPrefs

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReissueRetrofit