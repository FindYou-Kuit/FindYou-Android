package com.kuit.findu

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FindUApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // 항상 라이트 모드 강제
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient(BuildConfig.NAVER_CLIENT_ID)

        // AdMob 초기화
        MobileAds.initialize(this)
    }
}
