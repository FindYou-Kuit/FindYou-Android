package com.kuit.findu.presentation.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.findu.analytics.AnalyticsHelper
import com.kuit.findu.analytics.logUserSignIn
import com.kuit.findu.data.dataremote.exception.ApiNotFoundException
import com.kuit.findu.domain.usecase.SetNicknameUseCase
import com.kuit.findu.domain.usecase.auth.PostGuestLoginUseCase
import com.kuit.findu.domain.usecase.auth.PostLoginUseCase
import com.kuit.findu.domain.usecase.token.SetAccessTokenUseCase
import com.kuit.findu.domain.usecase.token.SetRefreshTokenUseCase
import com.kuit.findu.presentation.util.Nickname.GUEST_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PostLoginUseCase,
    private val guestLoginUseCase: PostGuestLoginUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
    private val setNicknameUseCase: SetNicknameUseCase,
    private val analyticsHelper: AnalyticsHelper,
) : ViewModel() {
    private val _startMainActivity = MutableSharedFlow<Unit>()
    val startMainActivity: SharedFlow<Unit> = _startMainActivity


    private val _startOnboardingActivity = MutableSharedFlow<Long>()
    val startOnboardingActivity: SharedFlow<Long> = _startOnboardingActivity

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    fun postLogin(kakaoId: Long) {
        viewModelScope.launch {
            loginUseCase.postLogin(kakaoId = kakaoId).onSuccess { loginData ->
                if (loginData.isFirstLogin) {
                    startOnboardingActivity(kakaoId = kakaoId)
                } else {
                    analyticsHelper.logUserSignIn(
                        userName = loginData.userInfo?.nickname ?: "Null Nickname", type = "kakao"
                    )
                    setAccessTokenUseCase(accessToken = loginData.userInfo!!.accessToken)
                    setRefreshTokenUseCase(refreshToken = loginData.userInfo.refreshToken)
                    setNicknameUseCase(nickname = loginData.userInfo.nickname)
                    startMainActivity()
                }
            }.onFailure { e ->
                Log.d("http", "Error Message: : $e")
            }
        }
    }

    fun postGuestLogin(onSuccess: () -> Unit) {
        viewModelScope.launch {
            guestLoginUseCase.postGuestLogin()
                .onSuccess { loginData ->
                    analyticsHelper.logUserSignIn(userName = GUEST_NAME, type = "Guest")
                    setAccessTokenUseCase(accessToken = loginData.accessToken)
                    setRefreshTokenUseCase(refreshToken = loginData.refreshToken)
                    setNicknameUseCase(nickname = GUEST_NAME)
                    onSuccess()
                    startMainActivity()
                }
                .onFailure { e ->
                    if(e is ApiNotFoundException) {
                        viewModelScope.launch {
                            _errorMessage.emit("가입된 계정이 있습니다.\n카카오 계정으로 로그인해주세요.")
                        }
                    }
                    Log.d("http", "Error Message: : $e")
                }
        }
    }


    private fun startMainActivity() {
        viewModelScope.launch {
            _startMainActivity.emit(Unit)
        }
    }

    private fun startOnboardingActivity(kakaoId: Long) {
        viewModelScope.launch {
            _startOnboardingActivity.emit(kakaoId)
        }
    }
}

