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
        getKeyHash()
    }

    private fun setKakao() {
        getKeyHash()
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }

    private fun getKeyHash() {
        Log.d("FindUApp1","getKeyHash key hash: ${Utility.getKeyHash(this)}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val packageInfo =
                this.packageManager.getPackageInfo(this.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            for (signature in packageInfo.signingInfo?.apkContentsSigners!!) {
                try {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    Log.d("FindUApp","getKeyHash key hash: ${Base64.encodeToString(md.digest(), Base64.NO_WRAP)}")
                } catch (e: NoSuchAlgorithmException) {
                    Log.w("FindUApp","getKeyHash Unable to get MessageDigest. signature=$signature", e)
                }
            }
        }
    }
}