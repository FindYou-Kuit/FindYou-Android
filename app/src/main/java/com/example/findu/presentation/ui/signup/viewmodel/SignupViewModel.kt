package com.example.findu.presentation.ui.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.PostCheckEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val checkEmailUseCase: PostCheckEmailUseCase
) : ViewModel() {
    private val _emailCheckResult = MutableStateFlow<Boolean?>(null)
    val emailCheckResult = _emailCheckResult.asStateFlow()

    fun checkEmail(email: String) {
        viewModelScope.launch {
            checkEmailUseCase(email).fold(
                onSuccess = { response ->
                    _emailCheckResult.value = !response.isDuplicateEmail
                },
                onFailure = {
                    _emailCheckResult.value = false
                }
            )
        }
    }
}