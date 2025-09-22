package com.example.findu.presentation.ui.extra.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.Department
import com.example.findu.domain.model.extra.VolunteerWork
import com.example.findu.presentation.model.HomeExtraContent
import com.example.findu.presentation.type.HomeExtraButtonType
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

data class HomeExtraUiState(
    val loadState: LoadState = LoadState.Idle,
    val homeExtraButtonType: HomeExtraButtonType? = null,
    val content: HomeExtraContent = HomeExtraContent.None

)

sealed class HomeExtraUiEvent {
    data object LoadData : HomeExtraUiEvent()
    data class SetHomeExtraType(val homeExtraButtonType: HomeExtraButtonType) : HomeExtraUiEvent()
}

sealed class HomeExtraUiEffect {

}


@HiltViewModel
class HomeExtraViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val HOME_EXTRA_TYPE = "homeExtraType"
    }


    private val _uiState = MutableStateFlow(HomeExtraUiState())

    private val _uiEffect = Channel<HomeExtraUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun handleEvent(event: HomeExtraUiEvent) {
        when (event) {
            is HomeExtraUiEvent.LoadData -> loadData()
            is HomeExtraUiEvent.SetHomeExtraType -> {
                _uiState.value = _uiState.value.copy(homeExtraButtonType = event.homeExtraButtonType)

            }
        }
    }

    val uiState = _uiState.onStart {
        val type: HomeExtraButtonType? = savedStateHandle[HOME_EXTRA_TYPE]
        type?.let {
            handleEvent(HomeExtraUiEvent.SetHomeExtraType(it))
        }
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = HomeExtraUiState()
    )

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loadState = LoadState.Loading)

            val content: HomeExtraContent = when (uiState.value.homeExtraButtonType) {
                HomeExtraButtonType.PROTECT_CENTER -> HomeExtraContent.Centers(dummyCenters)
                HomeExtraButtonType.PROTECT_DEPARTMENT -> HomeExtraContent.Departments(dummyDepartment)
                HomeExtraButtonType.VOLUNTEER -> HomeExtraContent.Volunteers(dummyVolunteer)
                null -> HomeExtraContent.None
            }

            _uiState.value = _uiState.value.copy(
                loadState = LoadState.Success,
                content = content
            )
        }
    }

    private val dummyVolunteer = listOf(
        VolunteerWork(
            institution = "양평군유기동물보호센터",
            recruitmentPeriod = "2025.04.21 ~ 2025.05.20",
            address = "경기도 양평군 어디리",
            workPeriod = "2025.04.21 ~ 2025.05.20",
            workTime = "09:00 ~ 10:00",
            webLink = "https://www.link.link"
        ), VolunteerWork(
            institution = "서울시 동물보호소",
            recruitmentPeriod = "2025.05.01 ~ 2025.05.31",
            address = "서울특별시 강남구 어딘가",
            workPeriod = "2025.06.01 ~ 2025.06.30",
            workTime = "13:00 ~ 17:00",
            webLink = "https://volunteer.seoul.go.kr"
        )
    )

    private val dummyDepartment = listOf(
        Department(
            name = "관광체육과 동물보호팀", district = "서울특별시 용산구", phone = "02-1234-1234"
        ), Department(
            name = "관광체육과 동물보호팀", district = "서울특별시 용산구", phone = "02-1234-1234"
        )
    )

    private val dummyCenters = listOf(
        Center(
            jurisdiction = listOf("서울특별시 강남구", "서울특별시 서초구"),
            centerName = "한국동물구조관리협회",
            phoneNumber = "053-764-3708",
            address = "서울특별시 강남구 삼성로1 삼성빌딩 1층"
        ),
        Center(
            jurisdiction = listOf("경기도 성남시 분당구", "경기도 용인시 수지구"),
            centerName = "경기동물보호센터",
            phoneNumber = "031-123-4567",
            address = "경기도 성남시 분당구 정자동 123"
        ),
        Center(
            jurisdiction = listOf("부산광역시 해운대구"),
            centerName = "부산유기동물보호센터",
            phoneNumber = "051-987-6543",
            address = "부산광역시 해운대구 반송동 45-1"
        ),
    )


}