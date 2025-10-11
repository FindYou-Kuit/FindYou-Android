package com.example.findu.presentation.ui.extra.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.extra.Sido
import com.example.findu.domain.usecase.extra.GetCentersUseCase
import com.example.findu.domain.usecase.extra.GetDepartmentsUseCase
import com.example.findu.domain.usecase.extra.GetSidoUseCase
import com.example.findu.domain.usecase.extra.GetSigunguUseCase
import com.example.findu.domain.usecase.extra.GetVolunteersUseCase
import com.example.findu.presentation.model.HomeExtraContent
import com.example.findu.presentation.type.HomeExtraButtonType
import com.example.findu.presentation.type.view.LoadState
import com.naver.maps.geometry.LatLng
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

data class HomeExtraUiState(
    val loadState: LoadState = LoadState.Idle,
    val homeExtraButtonType: HomeExtraButtonType? = null,
    val content: HomeExtraContent = HomeExtraContent.None,
    val selectedSido: Sido = Sido(id = 0, name = ""),
    val selectedSigungu: String = "",
    val sidoList: List<Sido> = emptyList(),
    val sigunguList: List<String> = emptyList(),
    val latitude: Double = 37.5642135,
    val longitude: Double = 127.0016985
)

sealed class HomeExtraUiEvent {
    data object LoadData : HomeExtraUiEvent()
    data class SetHomeExtraType(val homeExtraButtonType: HomeExtraButtonType) : HomeExtraUiEvent()
    data object GetSido : HomeExtraUiEvent()
    data class SidoSelected(val selectedSido: Sido) : HomeExtraUiEvent()
    data class SigunguSelected(val selectedSigungu: String) : HomeExtraUiEvent()
    data class UpdateLocation(val latitude: Double, val longitude: Double) : HomeExtraUiEvent()
    data class SearchCenterFocusedLatLng(val centerLatLng: LatLng) : HomeExtraUiEvent()
}

sealed class HomeExtraUiEffect {

}


@HiltViewModel
class HomeExtraViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getVolunteersUseCase: GetVolunteersUseCase,
    private val getCentersUseCase: GetCentersUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getSidoUseCase: GetSidoUseCase,
    private val getSigunguUseCase: GetSigunguUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeExtraViewModel"

        private const val HOME_EXTRA_TYPE = "homeExtraType"
        private const val EMPTY_STRING = ""
    }


    private val _uiState = MutableStateFlow(HomeExtraUiState())

    private val _uiEffect = Channel<HomeExtraUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun handleEvent(event: HomeExtraUiEvent) {
        when (event) {
            is HomeExtraUiEvent.LoadData -> loadData()
            is HomeExtraUiEvent.SetHomeExtraType -> {
                _uiState.update { it.copy(homeExtraButtonType = event.homeExtraButtonType) }
            }

            is HomeExtraUiEvent.SidoSelected -> {
                _uiState.update { it.copy(selectedSido = event.selectedSido, selectedSigungu = EMPTY_STRING) }
                getSigungu(event.selectedSido.id)
            }

            is HomeExtraUiEvent.SigunguSelected -> {
                _uiState.update { it.copy(selectedSigungu = event.selectedSigungu) }
                loadData()
            }

            is HomeExtraUiEvent.GetSido -> getSido()
            is HomeExtraUiEvent.UpdateLocation -> {
                _uiState.update { it.copy(latitude = event.latitude, longitude = event.longitude) }
            }
            is HomeExtraUiEvent.SearchCenterFocusedLatLng -> {
                getCenters(lat = event.centerLatLng.latitude , lon = event.centerLatLng.longitude)
            }
        }
    }

    val uiState = _uiState.onStart {
        val type: HomeExtraButtonType? = savedStateHandle[HOME_EXTRA_TYPE]
        type?.let {
            handleEvent(HomeExtraUiEvent.SetHomeExtraType(it))
        }
        if (type != HomeExtraButtonType.VOLUNTEER) {
            handleEvent(HomeExtraUiEvent.GetSido)
        }
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = HomeExtraUiState()
    )

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = LoadState.Loading) }

            when (uiState.value.homeExtraButtonType) {
                HomeExtraButtonType.PROTECT_CENTER -> getCenters()
                HomeExtraButtonType.PROTECT_DEPARTMENT -> getDepartments()
                HomeExtraButtonType.VOLUNTEER -> getVolunteers()
                null -> {
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Success,
                            content = HomeExtraContent.None
                        )
                    }
                }
            }
        }
    }


    private fun getCenters(
        sido: String = uiState.value.selectedSido.name,
        sigungu : String = uiState.value.selectedSigungu,
        lat : Double = uiState.value.latitude,
        lon:Double = uiState.value.longitude
                           ) {
        viewModelScope.launch {
            getCentersUseCase(
                sido = sido,
                sigungu = sigungu,
                lat = lat,
                lon = lon
            ).fold(
                onSuccess = { list ->
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Success,
                            content = HomeExtraContent.Centers(list.items)
                        )
                    }
                },
                onFailure = { e ->
                    Log.e(TAG, e.toString())
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Error,
                            content = HomeExtraContent.None
                        )
                    }
                }
            )
        }
    }

    private fun getDepartments() {
        viewModelScope.launch {
            getDepartmentsUseCase(district = uiState.value.selectedSido.name + uiState.value.selectedSigungu).fold(
                onSuccess = { list ->
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Success,
                            content = HomeExtraContent.Departments(list.items)
                        )
                    }
                },
                onFailure = { e ->
                    Log.e(TAG, e.toString())
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Error,
                            content = HomeExtraContent.None
                        )
                    }
                }
            )
        }
    }


    private fun getVolunteers() {
        viewModelScope.launch {
            getVolunteersUseCase().fold(
                onSuccess = { list ->
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Success,
                            content = HomeExtraContent.Volunteers(list.items)
                        )
                    }
                },
                onFailure = { e ->
                    Log.e(TAG, e.toString())
                    _uiState.update {
                        it.copy(
                            loadState = LoadState.Error,
                            content = HomeExtraContent.None
                        )
                    }
                }
            )
        }
    }

    private fun getSido() {
        viewModelScope.launch {
            getSidoUseCase().onSuccess { result ->
                _uiState.update { it.copy(sidoList = result) }
            }.onFailure {
                Log.e(TAG, it.toString())
            }
        }
    }

    private fun getSigungu(sidoId: Long) {
        viewModelScope.launch {
            getSigunguUseCase(sidoId).onSuccess { result ->
                _uiState.update { it.copy(sigunguList = result) }
            }.onFailure {
                Log.e(TAG, it.toString())
            }
        }
    }
}