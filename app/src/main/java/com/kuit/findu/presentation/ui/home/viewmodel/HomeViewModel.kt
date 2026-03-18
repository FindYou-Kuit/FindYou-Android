package com.kuit.findu.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.findu.domain.model.HomeData
import com.kuit.findu.domain.model.ProtectAnimal
import com.kuit.findu.domain.model.ReportAnimal
import com.kuit.findu.domain.usecase.GetIsGuestLoginUseCase
import com.kuit.findu.domain.usecase.GetNicknameUseCase
import com.kuit.findu.domain.usecase.home.GetHomeUseCase
import com.kuit.findu.presentation.type.HomeReportDurationType
import com.kuit.findu.presentation.type.HomeUserStatusType
import com.kuit.findu.presentation.type.view.LoadState
import com.kuit.findu.presentation.util.Nickname.GUEST_NAME
import com.google.firebase.perf.FirebasePerformance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val loadState: LoadState = LoadState.Idle,
    val homeData: HomeData? = null,
    val reportDataDuration: HomeReportDurationType = HomeReportDurationType.WEEK,
    val errorMessage: String? = null,
    val nickname: String = "",
    val isRefreshing: Boolean = false,
    val bannerCurrentPage: Int = 0,
    val isReportDialogVisible: Boolean = false,
    val locationPermission: Boolean = false,
) {
    val userHomeUserStatusType: HomeUserStatusType
        get() = when {
            !locationPermission -> HomeUserStatusType.LOCATION_DENIED
            nickname.isEmpty() || nickname.equals(
                GUEST_NAME,
                ignoreCase = false
            ) -> HomeUserStatusType.GUEST

            else -> HomeUserStatusType.MEMBER
        }
}

sealed class HomeUiEvent {
    data object LoadHomeData : HomeUiEvent()
    data object RefreshData : HomeUiEvent()
    data object ClearError : HomeUiEvent()

    data object OnReportDialogClick : HomeUiEvent()
    object OnReportDialogDismiss : HomeUiEvent()

    data object OnAlarmButtonClick : HomeUiEvent()

    data class OnHomeReportDurationClick(val duration: HomeReportDurationType) : HomeUiEvent()

    data class OnBannerPageChanged(val page: Int) : HomeUiEvent()

    data class SetLocationPermission(val locationPermission: Boolean) : HomeUiEvent()
    data object SetUserNickname : HomeUiEvent()

}

sealed class HomeUiEffect {
    data object NavigateToProtectList : HomeUiEffect()

    data object NavigateToReportList : HomeUiEffect()
    data class NavigateToProtectDetail(val animal: ProtectAnimal) : HomeUiEffect()
    data class NavigateToReportDetail(val animal: ReportAnimal) : HomeUiEffect()
    data object NavigateToLostReport : HomeUiEffect()
    data object NavigateToFindReport : HomeUiEffect()
    data class OpenWebLink(val url: String) : HomeUiEffect()
    data class ShowToast(val message: String) : HomeUiEffect()

    data object Dial : HomeUiEffect()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: GetHomeUseCase,
    private val getNicknameUseCase: GetNicknameUseCase,
    private val getIsGuestLoginUseCase: GetIsGuestLoginUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())

    private val _uiEffect = Channel<HomeUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    val uiState = _uiState
        .onStart { handleEvent(HomeUiEvent.LoadHomeData) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )


    fun handleEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.LoadHomeData -> loadHomeData()
            is HomeUiEvent.RefreshData -> refreshData()
            is HomeUiEvent.ClearError -> clearError()

            is HomeUiEvent.OnBannerPageChanged -> updateBannerPage(event.page)
            is HomeUiEvent.OnAlarmButtonClick -> alarmButtonClicked()
            is HomeUiEvent.OnHomeReportDurationClick -> changeReportDuration(event.duration)

            is HomeUiEvent.OnReportDialogClick -> {
                if (getIsGuestLoginUseCase()) {
                    viewModelScope.launch {
                        _uiEffect.send(HomeUiEffect.ShowToast("로그인 이후에 제보해주세요!"))
                    }
                } else {
                    _uiState.update { it.copy(isReportDialogVisible = true) }
                }
            }

            is HomeUiEvent.OnReportDialogDismiss -> {
                _uiState.update { it.copy(isReportDialogVisible = false) }
            }

            is HomeUiEvent.SetLocationPermission -> {
                _uiState.update { it.copy(locationPermission = event.locationPermission) }
            }

            HomeUiEvent.SetUserNickname -> setUserNickname()
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = LoadState.Loading) }
            val trace = FirebasePerformance.getInstance().newTrace("home_data_load")
            trace.start()
            homeUseCase().fold(
                onSuccess = { data ->
                    trace.putAttribute("status", "success")
                    trace.putMetric("protect_animal_count", data.protectAnimalCards.size.toLong())
                    trace.putMetric("report_animal_count", data.reportAnimalCards.size.toLong())
                    trace.stop()
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Success,
                            homeData = data,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    trace.putAttribute("status", "failure")
                    trace.stop()
                    Log.e("HomeViewModel", "loadHomeData: $error")
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Error,
                            errorMessage = error.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                        )
                    }
                }
            )
        }
    }


    private fun setUserNickname() {
        viewModelScope.launch {
            _uiState.update { it.copy(nickname = getNicknameUseCase()) }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            homeUseCase().fold(
                onSuccess = { data ->
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Success,
                            homeData = data,
                            errorMessage = null,
                            isRefreshing = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Error,
                            errorMessage = error.message ?: "데이터를 새로고침하는 중 오류가 발생했습니다.",
                            isRefreshing = false
                        )
                    }
                }
            )
        }
    }

    private fun clearError() {
        _uiState.update {
            it.copy(
                errorMessage = null,
                loadState = if (_uiState.value.homeData != null) LoadState.Success else LoadState.Idle
            )
        }
    }


    private fun changeReportDuration(duration: HomeReportDurationType) {
        viewModelScope.launch {
            _uiState.update { it.copy(reportDataDuration = duration) }
        }
    }

    private fun alarmButtonClicked() {
        //TODO: 추후 기능 추가
    }


    fun navigateToProtectList() {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.NavigateToProtectList)
        }
    }

    fun navigateToReportList() {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.NavigateToReportList)
        }
    }

    fun navigateToProtectDetail(animal: ProtectAnimal) {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.NavigateToProtectDetail(animal))
        }
    }

    fun navigateToReportDetail(animal: ReportAnimal) {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.NavigateToReportDetail(animal))
        }
    }

    fun navigateToLostReport() {
        viewModelScope.launch {
            if (getIsGuestLoginUseCase()) {
                _uiEffect.send(HomeUiEffect.ShowToast("로그인 이후에 제보해주세요!"))
            } else {
                _uiEffect.send(HomeUiEffect.NavigateToLostReport)
            }
        }
    }

    fun navigateToFindReport() {
        viewModelScope.launch {
            if (getIsGuestLoginUseCase()) {
                _uiEffect.send(HomeUiEffect.ShowToast("로그인 이후에 제보해주세요!"))
            } else {
                _uiEffect.send(HomeUiEffect.NavigateToFindReport)
            }
        }
    }

    fun dial() {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.Dial)
        }
    }

    private fun updateBannerPage(page: Int) {
        _uiState.update { it.copy(bannerCurrentPage = page) }
    }

}
