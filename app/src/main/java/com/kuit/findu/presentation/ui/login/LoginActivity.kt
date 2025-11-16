package com.kuit.findu.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kuit.findu.R
import com.kuit.findu.presentation.ui.login.composeview.LoginScreen
import com.kuit.findu.presentation.ui.login.viewmodel.LoginViewModel
import com.kuit.findu.presentation.ui.main.MainActivity
import com.kuit.findu.presentation.ui.onboarding.OnboardingActivity
import com.kuit.findu.presentation.util.extension.showToast
import com.kuit.findu.presentation.util.kakao.KakaoLoginHelper
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        private const val KAKAO_ID = "kakaoId"
    }

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val callback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, _ ->
                if (oAuthToken != null) {

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                            return@me
                        }

                        val kakaoId = user?.id
                        if (kakaoId != null) {
                            loginViewModel.postLogin(kakaoId = kakaoId)
                        } else {
                            Log.w(TAG, "KakaoId is null")
                        }
                    }
                }
            }
            LoginScreen(
                kakaoLoginButtonClicked = {
                    KakaoLoginHelper.login(
                        context = this,
                        callback = callback
                    )
                },
                withoutSignUpButtonClicked = {
                    loginViewModel.postGuestLogin {
                        this.showToast(message = getString(R.string.login_without_signup_toast_message))
                    }
                },
            )

        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    loginViewModel.startMainActivity.collect {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }

                launch {
                    loginViewModel.startOnboardingActivity.collect { kakaoId ->
                        startActivity(
                            Intent(this@LoginActivity, OnboardingActivity::class.java)
                                .putExtra(KAKAO_ID, kakaoId)
                        )
                        finish()
                    }
                }
            }
        }
    }
}