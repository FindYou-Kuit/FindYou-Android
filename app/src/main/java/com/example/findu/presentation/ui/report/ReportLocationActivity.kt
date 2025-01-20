package com.example.findu.presentation.ui.report

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewClientCompat
import com.example.findu.databinding.ActivityReportLocationBinding
import com.example.findu.presentation.ui.home.HomeFragment
import com.example.findu.presentation.ui.home.HomeFragment.Companion

class ReportLocationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityReportLocationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webView = binding.wvReportLocation
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler(ASSETS, AssetsPathHandler(this))
            .build()
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = LocalContentWebViewClient(assetLoader)

        // JavaScript 인터페이스 추가
        webView.addJavascriptInterface(BridgeInterface(), ANDROID)

        // HTML 파일 로드
        webView.loadUrl(ASSETS_URL)
    }

    inner class LocalContentWebViewClient(private val assetLoader: WebViewAssetLoader) :
        WebViewClientCompat() {
        override fun shouldInterceptRequest(
            view: WebView,
            request: WebResourceRequest
        ): WebResourceResponse? {
            return assetLoader.shouldInterceptRequest(request.url)
        }

        // To support API < 21.
        @Deprecated("Deprecated in Java")
        override fun shouldInterceptRequest(
            view: WebView,
            url: String
        ): WebResourceResponse? {
            return assetLoader.shouldInterceptRequest(Uri.parse(url))
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            view?.loadUrl(DAUM_POST_CODE)

        }
    }

    inner class BridgeInterface() {
        @JavascriptInterface
        fun processData(postData: String) {
            finish()
        }
    }

    companion object {
        private const val ASSETS = "/assets/"
        private const val ANDROID = "Android"
        private const val ASSETS_URL = "https://appassets.androidplatform.net/assets/html/address.html"
        private const val DAUM_POST_CODE = "javascript:sample2_execDaumPostcode();"
    }
}