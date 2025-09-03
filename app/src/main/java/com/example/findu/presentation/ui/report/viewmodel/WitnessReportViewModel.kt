package com.example.findu.presentation.ui.report.viewmodel

import android.net.Uri
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class WitnessReportUiState(
    val isFirstPermissionRequest: Boolean = true,
    val imageUriList: List<Uri> = emptyList(),
    val speciesType: SpeciesType? = null,
    val breedSearchText: TextFieldState = TextFieldState(),
    val breed: Breed? = null,
    val selectedFurColors: List<FurColorType> = emptyList(),
    val nowDate: LocalDateTime = LocalDateTime.now(),
    val witnessDate: String = "",
    val isDateBottomSheetShown: Boolean = false,
    val description: TextFieldState = TextFieldState(),
    val address: String = "",
    val currentLatLng: LatLng? = null,
    val nearPlace: TextFieldState = TextFieldState(),
    val isImageDialogShown: Boolean = false,
    val isSuccessDialogShown: Boolean = false,
    val isAppSettingDialogShown: Boolean = false,
)

sealed class WitnessReportUiEvent {
    data object OnBackPressed : WitnessReportUiEvent()
    data object OnAddImageClick : WitnessReportUiEvent()
    data object OnOpenCameraClick : WitnessReportUiEvent()
    data object OnOpenGalleryClick : WitnessReportUiEvent()
    data class OnImageSelected(val uri: Uri) : WitnessReportUiEvent()
    data object OnSelectAnimalInfoClick : WitnessReportUiEvent()
    data class OnSpeciesClick(val speciesType: SpeciesType) : WitnessReportUiEvent()
    data class OnBreedInputFieldClick(val input: String) : WitnessReportUiEvent()
    data class OnBreedClick(val breed: Breed) : WitnessReportUiEvent()
    data object OnInfoFinishButtonClick : WitnessReportUiEvent()
    data class OnFurColorSelected(
        val furColorType: FurColorType,
        val flag: Boolean,
    ) : WitnessReportUiEvent()

    data object OnWitnessDateClicked : WitnessReportUiEvent()
    data class OnDateSelected(val dateTime: LocalDateTime) : WitnessReportUiEvent()
    data object OnAddressSearchClick : WitnessReportUiEvent()
    data class OnAddressUpdated(val address: String) : WitnessReportUiEvent()
    data class OnMapPinMoved(val latLng: LatLng) : WitnessReportUiEvent()
    data object OnDismissDialog : WitnessReportUiEvent()
    data object OnReportFinishButtonClick : WitnessReportUiEvent()
    data object OnDismissKeyboard : WitnessReportUiEvent()
    data object OnAppSettingClick : WitnessReportUiEvent()
}

sealed class WitnessReportUiEffect {
    data object NavigateToUp : WitnessReportUiEffect()
    data object NavigateToAnimalInfo : WitnessReportUiEffect()
    data object NavigateToAddressSearch : WitnessReportUiEffect()
    data class ShowToast(val message: String) : WitnessReportUiEffect()
    data object ShowFinishDialog : WitnessReportUiEffect()
    data object DismissKeyboard : WitnessReportUiEffect()
    data object OpenCamera : WitnessReportUiEffect()
    data object OpenGallery : WitnessReportUiEffect()
    data object OpenAppSettings : WitnessReportUiEffect()
}

@HiltViewModel
class WitnessReportViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(WitnessReportUiState())
    val uiState: StateFlow<WitnessReportUiState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<WitnessReportUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun handleEvent(event: WitnessReportUiEvent) {
        when (event) {
            WitnessReportUiEvent.OnBackPressed -> {}
            WitnessReportUiEvent.OnAddImageClick -> setImageDialogVisible()
            WitnessReportUiEvent.OnAddressSearchClick -> {}
            is WitnessReportUiEvent.OnAddressUpdated -> {}
            is WitnessReportUiEvent.OnBreedClick -> {}
            is WitnessReportUiEvent.OnBreedInputFieldClick -> {}
            WitnessReportUiEvent.OnWitnessDateClicked -> {}
            is WitnessReportUiEvent.OnDateSelected -> {}
            is WitnessReportUiEvent.OnFurColorSelected -> {}
            WitnessReportUiEvent.OnInfoFinishButtonClick -> navigateUp()
            is WitnessReportUiEvent.OnMapPinMoved -> {}
            WitnessReportUiEvent.OnDismissDialog -> setDialogInVisible()
            WitnessReportUiEvent.OnOpenCameraClick -> openCamera()
            WitnessReportUiEvent.OnOpenGalleryClick -> openGallery()
            WitnessReportUiEvent.OnReportFinishButtonClick -> showFinishDialog()
            WitnessReportUiEvent.OnSelectAnimalInfoClick -> {}
            is WitnessReportUiEvent.OnSpeciesClick -> {}
            is WitnessReportUiEvent.OnImageSelected -> addImageToList(event.uri)
            WitnessReportUiEvent.OnDismissKeyboard -> {
                viewModelScope.launch {
                    _uiEffect.send(WitnessReportUiEffect.DismissKeyboard)
                }
            }

            WitnessReportUiEvent.OnAppSettingClick -> openAppSettings()
        }
    }

    private fun showFinishDialog() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.ShowFinishDialog)
        }
    }

    private fun navigateUp() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.NavigateToUp)
        }
    }

    private fun openAppSettings() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.OpenAppSettings)
        }
    }

    private fun openGallery() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.OpenGallery)
        }
    }

    fun openCamera() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.OpenCamera)
        }
    }

    fun setAppSettingDialogVisible() {
        _uiState.update {
            it.copy(isAppSettingDialogShown = true)
        }
    }

    private fun setImageDialogVisible() {
        _uiState.update {
            it.copy(isImageDialogShown = true)
        }
    }

    private fun setDialogInVisible() {
        _uiState.update {
            it.copy(
                isImageDialogShown = false,
                isSuccessDialogShown = false
            )
        }
    }

    private fun addImageToList(uri: Uri) {
        _uiState.update { it ->
            it.copy(
                imageUriList = listOf(uri) + it.imageUriList,
                isImageDialogShown = false
            )
        }
    }
}