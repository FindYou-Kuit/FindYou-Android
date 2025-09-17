package com.example.findu.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.domain.model.ReportAnimal
import com.example.findu.domain.usecase.GetHomeUseCase
import com.example.findu.presentation.type.HomeReportDurationType
import com.example.findu.presentation.type.view.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val loadState: LoadState = LoadState.Idle,
    val homeData: HomeData? = null,
    val reportDataDuration: HomeReportDurationType = HomeReportDurationType.WEEK,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
    val bannerCurrentPage: Int = 0,
    val isScrollToTopVisible: Boolean = false,
    val isReportDialogVisible: Boolean = false

)

sealed class HomeUiEvent {
    data object LoadHomeData : HomeUiEvent()
    data object RefreshData : HomeUiEvent()
    data object ClearError : HomeUiEvent()

    data object OnReportDialogClick : HomeUiEvent()
    object OnReportDialogDismiss : HomeUiEvent()

    data object OnAlarmButtonClick : HomeUiEvent()

    data class OnHomeReportDurationClick(val duration: HomeReportDurationType) : HomeUiEvent()

    data class OnBannerPageChanged(val page: Int) : HomeUiEvent()

    data class OnScrollPositionChanged(val firstVisibleItemIndex: Int) : HomeUiEvent()
}

sealed class HomeUiEffect {
    data object NavigateToProtectList : HomeUiEffect()

    data object NavigateToReportList : HomeUiEffect()
    data class NavigateToProtectDetail(val animal: ProtectAnimal) : HomeUiEffect()
    data class NavigateToReportDetail(val animal: ReportAnimal) : HomeUiEffect()
    data class OpenWebLink(val url: String) : HomeUiEffect()
    data class ShowToast(val message: String) : HomeUiEffect()

    data object Dial : HomeUiEffect()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: GetHomeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())

    private val _uiEffect = Channel<HomeUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    val uiState = _uiState
        .onStart {
            handleEvent(HomeUiEvent.LoadHomeData)
        }
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
            is HomeUiEvent.OnScrollPositionChanged -> updateScrollToTopVisibility(event.firstVisibleItemIndex)
            is HomeUiEvent.OnAlarmButtonClick -> alarmButtonClicked()
            is HomeUiEvent.OnHomeReportDurationClick -> changeReportDuration(event.duration)

            is HomeUiEvent.OnReportDialogClick -> {
                _uiState.value = _uiState.value.copy(isReportDialogVisible = true)
            }

            is HomeUiEvent.OnReportDialogDismiss -> {
                _uiState.value = _uiState.value.copy(isReportDialogVisible = false)
            }
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loadState = LoadState.Loading)

            homeUseCase().fold(
                onSuccess = { data ->
                    _uiState.value = _uiState.value.copy(
                        loadState = LoadState.Success,
                        homeData = data,
                        errorMessage = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        loadState = LoadState.Error,
                        errorMessage = error.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                    )
                }
            )
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)

            homeUseCase().fold(
                onSuccess = { data ->
                    _uiState.value = _uiState.value.copy(
                        loadState = LoadState.Success,
                        homeData = data,
                        errorMessage = null,
                        isRefreshing = false
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        loadState = LoadState.Error,
                        errorMessage = error.message ?: "데이터를 새로고침하는 중 오류가 발생했습니다.",
                        isRefreshing = false
                    )
                }
            )
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            loadState = if (_uiState.value.homeData != null) LoadState.Success else LoadState.Idle
        )
    }


    private fun changeReportDuration(duration: HomeReportDurationType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(reportDataDuration = duration)
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


    fun dial() {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.Dial)
        }
    }

    private fun updateBannerPage(page: Int) {
        _uiState.value = _uiState.value.copy(bannerCurrentPage = page)
    }

    private fun updateScrollToTopVisibility(firstVisibleItemIndex: Int) {
        val isVisible = firstVisibleItemIndex > 2 // 3번째 아이템 이후에 보이기
        _uiState.value = _uiState.value.copy(isScrollToTopVisible = isVisible)
    }

}