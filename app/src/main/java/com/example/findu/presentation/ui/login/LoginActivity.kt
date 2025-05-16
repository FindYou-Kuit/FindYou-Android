package com.example.findu.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.findu.presentation.ui.login.composeview.LoginScreen
import com.example.findu.presentation.ui.login.viewmodel.LoginViewModel
import com.example.findu.presentation.ui.main.MainActivity
import com.example.findu.presentation.util.extension.showToast
import com.example.findu.presentation.util.kakao.KakaoLoginHelper
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val callback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, _ ->
                if (oAuthToken != null) {
                    Log.d("ㅋㅋ","토큰: ${oAuthToken.accessToken}")
//                    viewModel.setKakaoAccessToken(oAuthToken.accessToken)
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
                    this.showToast("가입없이 찾아유 실행")
                    loginViewModel.startMainActivity()
                },
            )
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.startMainActivity.collect {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

}