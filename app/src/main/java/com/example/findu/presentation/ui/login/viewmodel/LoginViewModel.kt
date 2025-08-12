package com.example.findu.presentation.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.LoginInfo
import com.example.findu.domain.usecase.PostGuestLoginUseCase
import com.example.findu.domain.usecase.PostLoginUseCase
import com.example.findu.domain.usecase.token.SetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PostLoginUseCase,
    private val guestLoginUseCase: PostGuestLoginUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
) : ViewModel() {
    private val _startMainActivity = MutableSharedFlow<Unit>()
    val startMainActivity: SharedFlow<Unit> = _startMainActivity

    private val _startOnboardingActivity = MutableSharedFlow<Unit>()
    val startOnboardingActivity: SharedFlow<Unit> = _startOnboardingActivity


    private val deviceId = UUID.randomUUID().toString()

    fun postLogin(kakaoId: Long) {
        viewModelScope.launch {
            loginUseCase.postLogin(LoginInfo(kakaoId = kakaoId, deviceId = deviceId)).onSuccess { loginData ->
                if (loginData.isFirstLogin) {
                    startOnboardingActivity()
                } else {
                    setAccessTokenUseCase(accessToken = loginData.userInfo!!.accessToken)
                    startMainActivity()
                }
            }.onFailure { e ->
                Log.d("http", "Error Message: : $e")
            }
        }
    }


    fun postGuestLogin(onSuccess: () -> Unit) {
        viewModelScope.launch {
            guestLoginUseCase.postGuestLogin(deviceId = deviceId)
                .onSuccess { loginData ->
                    setAccessTokenUseCase(accessToken = loginData.accessToken)
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

    private fun startOnboardingActivity() {
        viewModelScope.launch {
            _startOnboardingActivity.emit(Unit)
        }
    }

}

