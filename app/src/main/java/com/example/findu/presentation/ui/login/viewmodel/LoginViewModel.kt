package com.example.findu.presentation.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: PostLoginUseCase
) : ViewModel() {
    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult = _loginResult.asStateFlow()

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
}
