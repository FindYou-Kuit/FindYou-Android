package com.example.findu.presentation.ui.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val profileImageUrl:String = "",
    val defaultProfileType: DefaultProfileType = DefaultProfileType.NONE,
    val nickName:String = "",
    val nickNameValidState:NicknameValidType = NicknameValidType.IDLE,
    val isNextButtonEnabled: Boolean = true,
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val _startMainActivity = MutableSharedFlow<Unit>()
    val startMainActivity: SharedFlow<Unit> = _startMainActivity

    fun onNextClicked() {
        viewModelScope.launch {
            if (_uiState.value.pageState == LAST_PAGE) {
                _startMainActivity.emit(Unit)
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

    fun changeDefaultProfile(defaultProfileType: DefaultProfileType){
        viewModelScope.launch {
            _uiState.update { it.copy(defaultProfileType = defaultProfileType) }
        }
    }

    fun startMainActivity() {
        viewModelScope.launch {
            _startMainActivity.emit(Unit)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val LAST_PAGE = 2
    }
}

