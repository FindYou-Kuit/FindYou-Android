package com.example.findu.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.HomeReportData
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.domain.model.ReportAnimal
import com.example.findu.domain.model.ReportDataType
import com.example.findu.domain.model.ReportItem
import com.example.findu.domain.usecase.GetHomeUseCase
import com.example.findu.presentation.type.AnimalStateType
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
    val homeReportData: HomeReportData = HomeReportData(
        reports = listOf(
            ReportItem(type = ReportDataType.RESCUE, count = 0),
            ReportItem(type = ReportDataType.PROTECTION, count = 0),
            ReportItem(type = ReportDataType.ADOPTION, count = 0),
            ReportItem(type = ReportDataType.REPORT, count = 0)
        )
    ),    val reportDataDuration: HomeReportDurationType = HomeReportDurationType.WEEK,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
    val bannerCurrentPage: Int = 0,
    val isScrollToTopVisible: Boolean = false
)

sealed class HomeUiEvent {
    data object LoadHomeData : HomeUiEvent()
    data object RefreshData : HomeUiEvent()
    data object ClearError : HomeUiEvent()

    data class OnProtectAnimalClick(val animal: ProtectAnimal) : HomeUiEvent()
    data class OnReportAnimalClick(val animal: ReportAnimal) : HomeUiEvent()
    data object OnReportDialogClick : HomeUiEvent()
    data object OnAlarmButtonClick : HomeUiEvent()
    data object OnFindDialogClick : HomeUiEvent()
    data class OnWebLinkClick(val url: String) : HomeUiEvent()
    data class OnHomeReportDurationClick(val duration: HomeReportDurationType) : HomeUiEvent()

    data class OnBannerPageChanged(val page: Int) : HomeUiEvent()

    data class OnScrollPositionChanged(val firstVisibleItemIndex: Int) : HomeUiEvent()
}

sealed class HomeUiEffect {
    data class NavigateToProtectDetail(val id: String, val tag: String, val name: String) : HomeUiEffect()
    data class NavigateToReportDetail(val id: String, val tag: String, val name: String) : HomeUiEffect()
    data object ShowReportDialog : HomeUiEffect()
    data object ShowFindDialog : HomeUiEffect()
    data class OpenWebLink(val url: String) : HomeUiEffect()

    data class ShowToast(val message: String) : HomeUiEffect()
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

            is HomeUiEvent.OnProtectAnimalClick -> navigateToProtectDetail(event.animal)
            is HomeUiEvent.OnReportAnimalClick -> navigateToReportDetail(event.animal)
            is HomeUiEvent.OnReportDialogClick -> showReportDialog()
            is HomeUiEvent.OnFindDialogClick -> showFindDialog()
            is HomeUiEvent.OnWebLinkClick -> openWebLink(event.url)

            is HomeUiEvent.OnBannerPageChanged -> updateBannerPage(event.page)
            is HomeUiEvent.OnScrollPositionChanged -> updateScrollToTopVisibility(event.firstVisibleItemIndex)
            is HomeUiEvent.OnAlarmButtonClick -> alarmButtonClicked()
            is HomeUiEvent.OnHomeReportDurationClick -> changeReportDuration(event.duration)
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
                        homeReportData = with(homeUseCase) {
                            data.toHomeReportData()
                        },
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

    private fun navigateToProtectDetail(animal: ProtectAnimal) {
        viewModelScope.launch {
            val tag = AnimalStateType.fromTag(animal.tag).state
            _uiEffect.send(
                HomeUiEffect.NavigateToProtectDetail(
                    id = animal.protectId.toString(),
                    tag = tag,
                    name = animal.title
                )
            )
        }
    }

    private fun navigateToReportDetail(animal: ReportAnimal) {
        viewModelScope.launch {
            val tag = AnimalStateType.fromTag(animal.tag).state
            _uiEffect.send(
                HomeUiEffect.NavigateToReportDetail(
                    id = animal.reportId.toString(),
                    tag = tag,
                    name = animal.title
                )
            )
        }
    }

    private fun showReportDialog() {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.ShowReportDialog)
        }
    }

    private fun changeReportDuration(duration: HomeReportDurationType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(reportDataDuration = duration)
        }
    }

    private fun alarmButtonClicked() {
        //TODO: 추후 기능 추가
    }

    private fun showFindDialog() {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.ShowFindDialog)
        }
    }

    private fun openWebLink(url: String) {
        viewModelScope.launch {
            _uiEffect.send(HomeUiEffect.OpenWebLink(url))
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