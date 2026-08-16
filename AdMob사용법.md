# AdMob 사용법

## 목차
1. [기본 세팅 (AdMob 사이트)](#1-기본-세팅-admob-사이트)
2. [프로젝트 세팅 (의존성, 프로퍼티 등)](#2-프로젝트-세팅-의존성-프로퍼티-등)
3. [xml View 에서 사용하는 방법](#3-xml-view-에서-사용하는-방법)
4. [JetPack Compose 에서 사용하는 방법](#4-jetpack-compose-에서-사용하는-방법)
5. [광고의 종류](#5-광고의-종류)

---

## 1. 기본 세팅 (AdMob 사이트)

### 1.1 AdMob 계정 생성
1. [AdMob 사이트](https://admob.google.com) 접속
2. Google 계정으로 로그인
3. AdMob 계정 생성 및 약관 동의

### 1.2 앱 등록
1. 좌측 메뉴 **앱** > **앱 추가** 클릭
2. 플랫폼 선택 (Android)
3. 앱 이름 입력 후 앱 추가

### 1.3 광고 단위 생성
1. 앱 선택 > **광고 단위** > **광고 단위 추가**
2. 광고 유형 선택 (배너, 전면, 리워드 등)
3. 광고 단위 이름 입력 후 생성
4. 생성된 **광고 단위 ID** 복사 (예: `ca-app-pub-xxxxxxxx/xxxxxxxxxx`)

### 1.4 필요한 ID 정리
| 항목 | 설명 | 예시 |
|------|------|------|
| **앱 ID** | AndroidManifest에 등록 | `ca-app-pub-7675272869453438~5374193050` |
| **광고 단위 ID** | 배너, 전면 등 광고별 ID | `ca-app-pub-7675272869453438/8613781671` |
| **테스트 배너 ID** | 디버그용 Google 공식 ID | `ca-app-pub-3940256099942544/6300978111` |

---

## 2. 프로젝트 세팅 (의존성, 프로퍼티 등)

### 2.1 의존성 추가 (app/build.gradle.kts)

```kotlin
dependencies {
    // AdMob
    implementation("com.google.android.gms:play-services-ads:23.1.0")
}
```

### 2.2 local.properties 설정

광고 ID를 시크릿으로 관리:

```properties
# AdMob
ADMOB_BANNER_ID_DEBUG = "ca-app-pub-3940256099942544/6300978111"
ADMOB_BANNER_ID_RELEASE = "ca-app-pub-7675272869453438/8613781671"
```

### 2.3 BuildConfig 설정 (app/build.gradle.kts)

```kotlin
val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    buildTypes {
        release {
            buildConfigField("String", "ADMOB_BANNER_ID", properties["ADMOB_BANNER_ID_RELEASE"].toString())
        }
        debug {
            buildConfigField("String", "ADMOB_BANNER_ID", properties["ADMOB_BANNER_ID_DEBUG"].toString())
        }
    }
}
```

### 2.4 AndroidManifest.xml 설정

```xml
<!-- 광고 ID 권한 -->
<uses-permission android:name="com.google.android.gms.permission.AD_ID"
    tools:ignore="AdvertisingIdPolicy" />

<application>
    <!-- AdMob App ID (앱 ID, 광고 단위 ID 아님!) -->
    <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="ca-app-pub-7675272869453438~5374193050" />
</application>
```

### 2.5 Application 초기화 (FindUApp.kt)

```kotlin
@HiltAndroidApp
class FindUApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // AdMob 초기화
        MobileAds.initialize(this)
    }
}
```

### 2.6 ProGuard 설정 (proguard-rules.pro)

```proguard
# ========== AdMob ==========
-keep class com.google.android.gms.ads.** { *; }
-dontwarn com.google.android.gms.ads.**
```

---

## 3. xml View 에서 사용하는 방법

### 3.1 레이아웃에 AdView 추가

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:ads="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.gms.ads.AdView
        android:id="@+id/adView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        ads:adSize="BANNER"
        ads:adUnitId="@string/admob_banner_id" />

</LinearLayout>
```

### 3.2 Activity/Fragment에서 광고 로드

```kotlin
class MyActivity : AppCompatActivity() {
    private lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        adView = findViewById(R.id.adView)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // 광고 이벤트 리스너 (선택)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Log.d("AdMob", "광고 로드 성공")
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.e("AdMob", "광고 로드 실패: ${error.message}")
            }
        }
    }
}
```

---

## 4. JetPack Compose 에서 사용하는 방법

### 4.1 AdBanner Composable (AdBanner.kt)

```kotlin
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
```

### 4.2 사용 예시

```kotlin
// HomeScreen.kt
AdBanner(
    modifier = Modifier.padding(horizontal = 20.dp),
    adLocation = "home_screen"
)

// HomeWebLinkList.kt
AdBanner(adLocation = "home_web_link")
```

### 4.3 Analytics 이벤트 (AnalyticsEvent.kt)

```kotlin
companion object {
    // AdMob Events
    const val AD_CLICK = "ad_click"
    const val AD_IMPRESSION = "ad_impression"
    const val AD_LOCATION = "ad_location"
}
```

---

## 5. 광고의 종류

### 5.1 배너 광고 (Banner)
- **특징**: 화면 일부에 표시되는 직사각형 광고
- **크기**: `BANNER` (320x50), `LARGE_BANNER` (320x100), `MEDIUM_RECTANGLE` (300x250)
- **현재 프로젝트**: HomeScreen, HomeWebLinkList에서 사용 중

### 5.2 전면 광고 (Interstitial)
- **특징**: 전체 화면을 덮는 광고
- **사용 시점**: 화면 전환 시 (예: 레벨 완료, 페이지 이동)
- **주의**: 사용자 경험을 해치지 않도록 적절한 타이밍에 표시

```kotlin
// 전면 광고 로드
InterstitialAd.load(context, AD_UNIT_ID, adRequest, object : InterstitialAdLoadCallback() {
    override fun onAdLoaded(ad: InterstitialAd) {
        interstitialAd = ad
    }
})

// 전면 광고 표시
interstitialAd?.show(activity)
```

### 5.3 리워드 광고 (Rewarded)
- **특징**: 사용자가 광고를 시청하면 보상 제공
- **사용 예시**: 게임 내 아이템, 프리미엄 기능 해제

```kotlin
// 리워드 광고 로드
RewardedAd.load(context, AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
    override fun onAdLoaded(ad: RewardedAd) {
        rewardedAd = ad
    }
})

// 리워드 광고 표시
rewardedAd?.show(activity) { reward ->
    // 보상 지급
    val rewardAmount = reward.amount
    val rewardType = reward.type
}
```

### 5.4 네이티브 광고 (Native)
- **특징**: 앱 UI에 자연스럽게 통합되는 맞춤형 광고
- **장점**: 사용자 경험과 일관된 디자인 가능

---

## 참고 자료
- [AdMob 공식 문서](https://developers.google.com/admob/android/quick-start)
- [Google 테스트 광고 ID](https://developers.google.com/admob/android/test-ads)
