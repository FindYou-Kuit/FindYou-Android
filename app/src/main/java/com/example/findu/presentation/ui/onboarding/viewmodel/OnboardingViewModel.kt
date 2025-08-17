package com.example.findu.presentation.ui.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.PostCheckNicknameUseCase
import com.example.findu.presentation.type.DefaultProfileType
import com.example.findu.presentation.type.NicknameValidType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val pageState: Int = 1,
    val profileImageUrl: String = "",
    val defaultProfileType: DefaultProfileType = DefaultProfileType.NONE,
    val nickname: String = "",
    val nickNameValidState: NicknameValidType = NicknameValidType.IDLE,
    val isNextButtonEnabled: Boolean = true,
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val postCheckNicknameUseCase: PostCheckNicknameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _startMainActivity = MutableSharedFlow<Unit>()
    val startMainActivity: SharedFlow<Unit> = _startMainActivity

    fun onNextClicked() {
        viewModelScope.launch {
            if (_uiState.value.pageState == LAST_PAGE) {
                startMainActivity()
            } else {
                _uiState.update { it.copy(pageState = LAST_PAGE, isNextButtonEnabled = false) }
            }
        }
    }

    fun onBackButtonClicked() {
        viewModelScope.launch {
            if (_uiState.value.pageState == LAST_PAGE) {
                _uiState.update { it.copy(pageState = FIRST_PAGE, isNextButtonEnabled = true) }
            }
        }
    }

    fun changeDefaultProfile(defaultProfileType: DefaultProfileType) {
        viewModelScope.launch {
            _uiState.update { it.copy(defaultProfileType = defaultProfileType) }
        }
    }

    fun onNicknameValueChanged(nickname: String) {
        val validState = when {
            nickname.isEmpty() -> NicknameValidType.EMPTY_INVALID
            containsSpecialCharacter(nickname) -> NicknameValidType.FORMAT_INVALID
            else -> NicknameValidType.FOCUS
        }
        viewModelScope.launch {
            changeNextButtonEnabled(false)
            _uiState.update { it.copy(nickname = nickname, nickNameValidState = validState) }
        }
    }

    fun focusChanged(isFocused: Boolean) {
        if (isFocused) {
            if (_uiState.value.nickNameValidState == NicknameValidType.IDLE)
                viewModelScope.launch { _uiState.update { it.copy(nickNameValidState = NicknameValidType.FOCUS) } }
        } else {
            if (_uiState.value.nickNameValidState == NicknameValidType.FOCUS)
                viewModelScope.launch { _uiState.update { it.copy(nickNameValidState = NicknameValidType.IDLE) } }
        }
    }

    fun nicknameDuplicateCheck() {
        viewModelScope.launch {
            if (uiState.value.nickname.isEmpty()) {
                _uiState.update { it.copy(nickNameValidState = NicknameValidType.EMPTY_INVALID) }
            } else {
                postCheckNicknameUseCase(uiState.value.nickname).onSuccess { isDuplicate ->
                    if (isDuplicate) {
                        _uiState.update { it.copy(nickNameValidState = NicknameValidType.DUPLICATE_INVALID) }
                    } else {
                        _uiState.update { it.copy(nickNameValidState = NicknameValidType.VALID) }
                        changeNextButtonEnabled(true)
                    }
                }.onFailure { e ->
                    Log.d("http", "Error Message: : $e")
                }
                focusChanged(false)
            }
        }
    }

    fun setProfileImage(uri: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(profileImageUrl = uri) }
        }
    }

    private fun changeNextButtonEnabled(enabled: Boolean) {
        when (enabled) {
            true -> {
                if (!_uiState.value.isNextButtonEnabled) {
                    _uiState.update { it.copy(isNextButtonEnabled = true) }
                }
            }

            false -> {
                if (_uiState.value.isNextButtonEnabled) {
                    _uiState.update { it.copy(isNextButtonEnabled = false) }
                }
            }
        }
    }

    private fun startMainActivity() {
        viewModelScope.launch {
            _startMainActivity.emit(Unit)
        }
    }

    private fun containsSpecialCharacter(input: String): Boolean {
        val regex = Regex("[^a-zA-Z0-9가-힣]")
        return regex.containsMatchIn(input)
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val LAST_PAGE = 2
    }
}

