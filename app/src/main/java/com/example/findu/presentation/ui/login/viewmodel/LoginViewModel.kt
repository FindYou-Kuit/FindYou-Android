package com.example.findu.presentation.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.PostGuestLoginUseCase
import com.example.findu.domain.usecase.PostLoginUseCase
import com.example.findu.domain.usecase.SetNicknameUseCase
import com.example.findu.domain.usecase.token.SetAccessTokenUseCase
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
    private val setNicknameUseCase: SetNicknameUseCase
) : ViewModel() {
    private val _startMainActivity = MutableSharedFlow<Unit>()
    val startMainActivity: SharedFlow<Unit> = _startMainActivity


    private val _startOnboardingActivity = MutableSharedFlow<Long>()
    val startOnboardingActivity: SharedFlow<Long> = _startOnboardingActivity

    fun postLogin(kakaoId: Long) {
        viewModelScope.launch {
            loginUseCase.postLogin(kakaoId = kakaoId).onSuccess { loginData ->
                if (loginData.isFirstLogin) {
                    startOnboardingActivity(kakaoId = kakaoId)
                } else {
                    setAccessTokenUseCase(accessToken = loginData.userInfo!!.accessToken)
                    setNicknameUseCase(nickname = loginData.userInfo.nickname)
                    Log.d("http", "nickname: ${loginData.userInfo.nickname}")
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
                    setAccessTokenUseCase(accessToken = loginData.accessToken)
                    setNicknameUseCase(nickname = "사용자")
                    onSuccess()
                    startMainActivity()
                }
                .onFailure { e ->
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

