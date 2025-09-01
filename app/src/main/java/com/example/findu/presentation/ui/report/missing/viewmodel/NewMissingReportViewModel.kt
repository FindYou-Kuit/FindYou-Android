package com.example.findu.presentation.ui.report.missing.viewmodel

import android.net.Uri
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.model.report.Gender
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

data class MissingReportUiState(
    val imageUriList: List<Uri> = emptyList(),
    val speciesType: SpeciesType? = null,
    val breedSearchText: TextFieldState = TextFieldState(),
    val breed: Breed? = null,
    val age: TextFieldState = TextFieldState(),
    val gender: Gender = Gender.MALE,
    val rfidNumber: TextFieldState = TextFieldState(),
    val selectedFurColors: List<FurColorType> = emptyList(),
    val nowDate: LocalDateTime = LocalDateTime.now(),
    val missingDate: String = "",
    val isDateBottomSheetShown: Boolean = false,
    val description: String = "",
    val address: String = "",
    val currentLatLng: LatLng? = null,
    val nearPlace: String = "",
)
sealed class MissingReportUiEvent {
    data object OnBackPressed : MissingReportUiEvent()
    data object OnAddImageClick : MissingReportUiEvent()
    data object OnOpenCameraClick : MissingReportUiEvent()
    data object OnOpenGalleryClick : MissingReportUiEvent()
    data object OnSelectAnimalInfoClick : MissingReportUiEvent()
    data class OnSpeciesClick(val speciesType: SpeciesType) : MissingReportUiEvent()
    data class OnBreedInputFieldClick(val input: String) : MissingReportUiEvent()
    data class OnBreedClick(val breed: Breed) : MissingReportUiEvent()
    data object OnInfoFinishButtonClick : MissingReportUiEvent()
    data class OnGenderSelected(val gender: Gender) : MissingReportUiEvent()
    data class OnFurColorSelected(
        val furColorType: FurColorType,
        val flag: Boolean,
    ) : MissingReportUiEvent()

    data object OnMissingDateClicked : MissingReportUiEvent()
    data class OnDateSelected(val dateTime: LocalDateTime) : MissingReportUiEvent()
    data object OnAddressSearchClick : MissingReportUiEvent()
    data class OnAddressUpdated(val address: String) : MissingReportUiEvent()
    data class OnMapPinMoved(val latLng: LatLng) : MissingReportUiEvent()
    data object OnReportFinishButtonClick : MissingReportUiEvent()
    data object OnNavigateReportHistoryClick : MissingReportUiEvent()
    data object OnNavigateHomeClick : MissingReportUiEvent()
    data object OnDismissKeyboard : MissingReportUiEvent()
}

sealed class MissingReportUiEffect {
    data object NavigateToAnimalInfo : MissingReportUiEffect()
    data object NavigateToAddressSearch : MissingReportUiEffect()
    data class ShowToast(val message: String) : MissingReportUiEffect()
    data object DismissKeyboard : MissingReportUiEffect()
}

@HiltViewModel
class NewMissingReportViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MissingReportUiState())
    val uiState: StateFlow<MissingReportUiState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<MissingReportUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun handleEvent(event: MissingReportUiEvent) {
        when (event) {
            MissingReportUiEvent.OnBackPressed -> {}
            MissingReportUiEvent.OnAddImageClick -> {}
            MissingReportUiEvent.OnAddressSearchClick -> {}
            is MissingReportUiEvent.OnAddressUpdated -> {}
            is MissingReportUiEvent.OnBreedClick -> {}
            is MissingReportUiEvent.OnBreedInputFieldClick -> {}
            MissingReportUiEvent.OnMissingDateClicked -> {}
            is MissingReportUiEvent.OnDateSelected -> {}
            is MissingReportUiEvent.OnFurColorSelected -> {}
            is MissingReportUiEvent.OnGenderSelected -> {}
            MissingReportUiEvent.OnInfoFinishButtonClick -> {}
            is MissingReportUiEvent.OnMapPinMoved -> {}
            MissingReportUiEvent.OnNavigateHomeClick -> {}
            MissingReportUiEvent.OnNavigateReportHistoryClick -> {}
            MissingReportUiEvent.OnOpenCameraClick -> {}
            MissingReportUiEvent.OnOpenGalleryClick -> {}
            MissingReportUiEvent.OnReportFinishButtonClick -> {}
            MissingReportUiEvent.OnSelectAnimalInfoClick -> {}
            is MissingReportUiEvent.OnSpeciesClick -> {}
            MissingReportUiEvent.OnDismissKeyboard -> {
                viewModelScope.launch {
                    _uiEffect.send(MissingReportUiEffect.DismissKeyboard)
                }
            }
        }
    }
}