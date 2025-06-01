package com.example.findu

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import com.example.findu.BuildConfig.KAKAO_NATIVE_APP_KEY
import dagger.hilt.android.HiltAndroidApp
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

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