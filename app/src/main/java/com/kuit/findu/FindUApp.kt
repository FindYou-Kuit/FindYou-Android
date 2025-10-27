package com.kuit.findu

import android.app.Application
import com.kuit.findu.BuildConfig.KAKAO_NATIVE_APP_KEY
import dagger.hilt.android.HiltAndroidApp
import com.kakao.sdk.common.KakaoSdk

@HiltAndroidApp
class FindUApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setKakao()
    }

    private fun setKakao() {
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }
}