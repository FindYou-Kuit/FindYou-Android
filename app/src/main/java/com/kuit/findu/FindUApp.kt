package com.kuit.findu

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.kakao.sdk.common.KakaoSdk
import com.kuit.findu.data.dataremote.util.SessionExpiredEventManager
import com.kuit.findu.presentation.ui.login.LoginActivity
import com.kuit.findu.presentation.ui.splash.SplashActivity
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltAndroidApp
class FindUApp : Application() {

    @Inject
    lateinit var sessionExpiredEventManager: SessionExpiredEventManager

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var currentActivity: WeakReference<Activity>? = null

    override fun onCreate() {
        super.onCreate()

        // 항상 라이트 모드 강제
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient(BuildConfig.NAVER_CLIENT_ID)

        // AdMob 초기화
        MobileAds.initialize(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityResumed(activity: Activity) {
                currentActivity = WeakReference(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                if (currentActivity?.get() === activity) {
                    currentActivity = null
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })

        applicationScope.launch {
            sessionExpiredEventManager.sessionExpiredEvent.collect {
                val activity = currentActivity?.get() ?: return@collect
                if (activity is LoginActivity || activity is SplashActivity) return@collect

                val intent = Intent(activity, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                activity.startActivity(intent)
            }
        }
    }
}
