package com.example.findu.presentation.ui.extra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.extra.VolunteerWork
import com.example.findu.presentation.type.view.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeExtraUiState(
    val loadState: LoadState = LoadState.Idle,
    val data: List<VolunteerWork> = listOf(
        VolunteerWork(
            institution = "양평군유기동물보호센터",
            recruitmentPeriod = "2025.04.21 ~ 2025.05.20",
            address = "경기도 양평군 어디리",
            workPeriod = "2025.04.21 ~ 2025.05.20",
            workTime = "09:00 ~ 10:00",
            webLink = "https://www.link.link"
        ),
        VolunteerWork(
            institution = "서울시 동물보호소",
            recruitmentPeriod = "2025.05.01 ~ 2025.05.31",
            address = "서울특별시 강남구 어딘가",
            workPeriod = "2025.06.01 ~ 2025.06.30",
            workTime = "13:00 ~ 17:00",
            webLink = "https://volunteer.seoul.go.kr"
        )
    )
)

sealed class HomeExtraUiEvent {

}

sealed class HomeExtraUiEffect {

}

@HiltViewModel
class HomeExtraViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeExtraUiState())

    private val _uiEffect = Channel<HomeExtraUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    val uiState = _uiState
        .onStart {
            _uiState.value = HomeExtraUiState(loadState = LoadState.Success)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeExtraUiState()
        )


}