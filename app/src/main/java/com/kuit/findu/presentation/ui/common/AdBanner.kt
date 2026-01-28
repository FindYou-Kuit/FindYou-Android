package com.kuit.findu.presentation.ui.common

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.firebase.analytics.FirebaseAnalytics
import com.kuit.findu.BuildConfig
import com.kuit.findu.analytics.AnalyticsEvent

private val AD_UNIT_ID = BuildConfig.ADMOB_BANNER_ID

@Composable
fun AdBanner(
    modifier: Modifier = Modifier,
    adLocation: String = "unknown"
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            val analytics = FirebaseAnalytics.getInstance(context)

            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = AD_UNIT_ID
                adListener = object : AdListener() {
                    override fun onAdClicked() {
                        analytics.logEvent(AnalyticsEvent.AD_CLICK, Bundle().apply {
                            putString(AnalyticsEvent.AD_LOCATION, adLocation)
                        })
                    }

                    override fun onAdImpression() {
                        analytics.logEvent(AnalyticsEvent.AD_IMPRESSION, Bundle().apply {
                            putString(AnalyticsEvent.AD_LOCATION, adLocation)
                        })
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        Log.e("AdBanner", "광고 로드 실패: ${error.code} - ${error.message}")
                    }

                    override fun onAdLoaded() {
                        Log.d("AdBanner", "광고 로드 성공")
                    }
                }
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
