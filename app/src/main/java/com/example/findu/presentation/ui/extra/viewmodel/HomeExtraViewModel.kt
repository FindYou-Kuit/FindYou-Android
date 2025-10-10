package com.example.findu.presentation.ui.extra.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.extra.GetCentersUseCase
import com.example.findu.domain.usecase.extra.GetDepartmentsUseCase
import com.example.findu.domain.usecase.extra.GetVolunteersUseCase
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
import kotlinx.coroutines.flow.update
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
    private val savedStateHandle: SavedStateHandle,
    private val getVolunteersUseCase: GetVolunteersUseCase,
    private val getCentersUseCase: GetCentersUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "HomeExtraViewModel"

        private const val HOME_EXTRA_TYPE = "homeExtraType"
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
            _uiState.update { it.copy(loadState = LoadState.Loading) }

            when (uiState.value.homeExtraButtonType) {
                HomeExtraButtonType.PROTECT_CENTER -> {
                    getCentersUseCase().fold(
                        onSuccess = { list ->
                            _uiState.value = _uiState.value.copy(
                                loadState = LoadState.Success,
                                content = HomeExtraContent.Centers(list.items)
                            )
                        },
                        onFailure = { e ->
                            Log.e(TAG,e.toString())
                            _uiState.value = _uiState.value.copy(
                                loadState = LoadState.Error,
                                content = HomeExtraContent.None
                            )
                        }
                    )
                }

                HomeExtraButtonType.PROTECT_DEPARTMENT -> {
                    getDepartmentsUseCase().fold(
                        onSuccess = { list ->
                            _uiState.value = _uiState.value.copy(
                                loadState = LoadState.Success,
                                content = HomeExtraContent.Departments(list.items)
                            )
                        },
                        onFailure = { e ->
                            Log.e(TAG,e.toString())
                            _uiState.value = _uiState.value.copy(
                                loadState = LoadState.Error,
                                content = HomeExtraContent.None
                            )
                        }
                    )
                }

                HomeExtraButtonType.VOLUNTEER -> {
                    getVolunteersUseCase().fold(
                        onSuccess = { list ->
                            _uiState.value = _uiState.value.copy(
                                loadState = LoadState.Success,
                                content = HomeExtraContent.Volunteers(list.items)
                            )
                        },
                        onFailure = { e ->
                            Log.e(TAG,e.toString())
                            _uiState.value = _uiState.value.copy(
                                loadState = LoadState.Error,
                                content = HomeExtraContent.None
                            )
                        }
                    )
                }

                null -> {
                    _uiState.value = _uiState.value.copy(
                        loadState = LoadState.Success,
                        content = HomeExtraContent.None
                    )
                }
            }
        }
    }
}