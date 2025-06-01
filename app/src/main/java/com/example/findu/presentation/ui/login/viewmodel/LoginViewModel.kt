package com.example.findu.presentation.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.PostLoginUseCase
import com.example.findu.domain.usecase.token.SetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PostLoginUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
) : ViewModel() {
    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult = _loginResult.asStateFlow()

    private val _startMainActivity = MutableSharedFlow<Unit>()
    val startMainActivity: SharedFlow<Unit> = _startMainActivity

    private val _startOnboardingActivity = MutableSharedFlow<Unit>()
    val startOnboardingActivity: SharedFlow<Unit> = _startOnboardingActivity

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase.postLogin(email, password).fold(
                onSuccess = {
                    _loginResult.value = true
                },
                onFailure = { _loginResult.value = false }
            )
        }
    }

    fun setAccessToken(accessToken:String){
        setAccessTokenUseCase(accessToken)
    }

    fun checkRegisteredUser(oAuthToken:String){
        //TODO: 회원가입 여부 API 나오면 oAuthToken인자로 넣어서 호출
        val response = dummyRegisteredCheckLogic(oAuthToken)

        if (response){
            //TODO: 로그인 API 호출 후 응답으로 받은 accessToken 저장
//            startMainActivity()
            startOnboardingActivity()
        }else{
            startOnboardingActivity()
        }
    }


    fun startMainActivity() {
        viewModelScope.launch {
            _startMainActivity.emit(Unit)
        }
    }

    private fun startOnboardingActivity() {
        viewModelScope.launch {
            _startOnboardingActivity.emit(Unit)
        }
    }

    private fun dummyRegisteredCheckLogic(oAuthToken: String):Boolean{
        return oAuthToken.isEmpty()
    }

}

