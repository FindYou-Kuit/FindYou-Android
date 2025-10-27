package com.kuit.findu.presentation.ui.onboarding.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.findu.domain.usecase.PostCheckNicknameUseCase
import com.kuit.findu.domain.usecase.PostSignupUseCase
import com.kuit.findu.presentation.type.DefaultProfileType
import com.kuit.findu.presentation.type.NicknameValidType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class OnboardingUiState(
    val pageState: Int = 1,
    val kakaoId:Long = -1L,
    val profileImageUri: Uri? = null,
    val defaultProfileType: DefaultProfileType = DefaultProfileType.DEFAULT,
    val nickname: String = "",
    val nickNameValidState: NicknameValidType = NicknameValidType.IDLE,
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postCheckNicknameUseCase: PostCheckNicknameUseCase,
    private val postSignupUseCase: PostSignupUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _startMainActivity = MutableSharedFlow<Unit>()
    val startMainActivity: SharedFlow<Unit> = _startMainActivity

    fun onNextClicked() {
        viewModelScope.launch {
            if (_uiState.value.pageState == LAST_PAGE) {
                signUp()
            } else {
                _uiState.update { it.copy(pageState = LAST_PAGE) }
            }
        }
    }

    fun onBackButtonClicked() {
        viewModelScope.launch {
            if (_uiState.value.pageState == LAST_PAGE) {
                _uiState.update { it.copy(pageState = FIRST_PAGE) }
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
                    }
                }.onFailure { e ->
                    Log.d("http", "Error Message: : $e")
                }
                focusChanged(false)
            }
        }
    }

    fun setProfileImage(uri: Uri?) {
        viewModelScope.launch {
            _uiState.update { it.copy(profileImageUri = uri) }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            postSignupUseCase.postSignup(
                profileImageFile = uiState.value.profileImageUri?.let { uriToFile(uri = it) },
                defaultImageName = uiState.value.defaultProfileType.string,
                nickname = uiState.value.nickname,
                kakaoId = uiState.value.kakaoId
            ).onSuccess {
                startMainActivity()
            }.onFailure {e->
                Log.d("http", "Error Message: : $e")
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

    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File.createTempFile("profile_", ".jpg", context.cacheDir)
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setKakaoId(id: Long) {
        _uiState.update { it.copy(kakaoId = id) }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val LAST_PAGE = 2
    }
}

