package com.example.findu.presentation.ui.report.viewmodel

import android.net.Uri
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.presentation.util.extension.toNormalizeAddress
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

data class WitnessReportUiState(
    val isFirstPermissionRequest: Boolean = true,
    val imageUriList: List<Uri> = emptyList(),
    val speciesType: SpeciesType? = null,
    val breedSearchText: TextFieldState = TextFieldState(),
    val breed: Breed? = null,
    val breedList: List<Breed> = emptyList(),
    val selectedFurColors: List<FurColorType> = emptyList(),
    val nowDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
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
    data class OnAIDistinctionClick(val uri: Uri) : WitnessReportUiEvent()
    data object OnSelectAnimalInfoClick : WitnessReportUiEvent()
    data class OnSpeciesClick(val speciesType: SpeciesType) : WitnessReportUiEvent()
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
    data object ClearFocus : WitnessReportUiEvent()
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
    data object ClearFocus : WitnessReportUiEffect()
}

@HiltViewModel
class WitnessReportViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(WitnessReportUiState())
    val uiState: StateFlow<WitnessReportUiState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<WitnessReportUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        fetchBreedList()
    }

    private fun fetchBreedList() {
        // TODO: 실제 API 연동 필요
        _uiState.update {
            it.copy(
                breedList = listOf(
                    Breed.DogBreed(1, "Labrador Retriever", SpeciesType.DOG),
                    Breed.DogBreed(2, "German Shepherd", SpeciesType.DOG),
                    Breed.DogBreed(3, "Golden Retriever", SpeciesType.DOG),
                    Breed.DogBreed(4, "Bulldog", SpeciesType.DOG),
                    Breed.DogBreed(5, "Beagle", SpeciesType.DOG),
                    Breed.DogBreed(6, "Poodle", SpeciesType.DOG),
                )
            )
        }
    }

    fun handleEvent(event: WitnessReportUiEvent) {
        when (event) {
            WitnessReportUiEvent.OnBackPressed -> navigateUp()
            WitnessReportUiEvent.OnAddImageClick -> setImageDialogVisible()
            is WitnessReportUiEvent.OnAIDistinctionClick -> distinguishWithAI(event.uri)
            WitnessReportUiEvent.OnAddressSearchClick -> navigateToAddressSearch()
            is WitnessReportUiEvent.OnAddressUpdated -> updateAddress(event.address)
            is WitnessReportUiEvent.OnBreedClick -> updateBreed(event.breed)
            WitnessReportUiEvent.OnWitnessDateClicked -> setDateBottomSheetVisible(true)
            is WitnessReportUiEvent.OnDateSelected -> updateDate(event.dateTime)
            is WitnessReportUiEvent.OnFurColorSelected ->
                updateFurColor(event.furColorType, event.flag)

            WitnessReportUiEvent.OnInfoFinishButtonClick -> navigateUp()
            is WitnessReportUiEvent.OnMapPinMoved -> updateAddress(event.latLng)
            WitnessReportUiEvent.OnDismissDialog -> setDialogInVisible()
            WitnessReportUiEvent.OnOpenCameraClick -> openCamera()
            WitnessReportUiEvent.OnOpenGalleryClick -> openGallery()
            WitnessReportUiEvent.OnReportFinishButtonClick -> postMissingReport()
            WitnessReportUiEvent.OnSelectAnimalInfoClick -> navigateToAnimalInfo()
            is WitnessReportUiEvent.OnSpeciesClick -> updateSpecies(event.speciesType)
            is WitnessReportUiEvent.OnImageSelected -> addImageToList(event.uri)
            WitnessReportUiEvent.OnDismissKeyboard -> dismissKeyboard()
            WitnessReportUiEvent.OnAppSettingClick -> openAppSettings()
            WitnessReportUiEvent.ClearFocus -> clearFocus()
        }
    }

    private fun postMissingReport() {
        // TODO : 신고 등록 API 구현
        val normalizedAddress = _uiState.value.address.toNormalizeAddress()
        showFinishDialog()
    }

    private fun distinguishWithAI(uri: Uri) {
        // TODO : AI api 연동
    }

    private fun clearFocus() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.ClearFocus)
        }
    }

    private fun dismissKeyboard() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.DismissKeyboard)
        }
    }

    private fun updateAddress(latLng: LatLng) {
        // TODO: 주소 변환 api 연동
        _uiState.update {
            it.copy(
                currentLatLng = latLng,
                address = "위도: ${latLng.latitude}, 경도: ${latLng.longitude}"
            )
        }
    }

    private fun updateFurColor(
        furColorType: FurColorType,
        flag: Boolean,
    ) {
        _uiState.update {
            val newList = if (flag) {
                it.selectedFurColors + furColorType
            } else {
                it.selectedFurColors - furColorType
            }
            it.copy(selectedFurColors = newList)
        }
    }

    private fun updateSpecies(speciesType: SpeciesType) {
        _uiState.update {
            it.copy(
                speciesType = speciesType,
                breed = null,
                breedSearchText = TextFieldState(""),
            )
        }
    }

    private fun navigateToAnimalInfo() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.NavigateToAnimalInfo)
        }
    }

    private fun updateDate(dateTime: LocalDateTime) {
        _uiState.update {
            it.copy(
                witnessDate = "${dateTime.year}년 ${dateTime.monthNumber}월 ${dateTime.dayOfMonth}일",
            )
        }
        setDateBottomSheetVisible(false)
    }

    private fun setDateBottomSheetVisible(flag: Boolean) {
        _uiState.update { it.copy(isDateBottomSheetShown = flag) }
    }

    private fun updateBreed(breed: Breed) {
        _uiState.update {
            it.copy(
                breed = breed,
                breedSearchText = TextFieldState(breed.name)
            )
        }
    }

    private fun updateAddress(address: String) {
        _uiState.update { it.copy(address = address) }
    }

    private fun navigateToAddressSearch() {
        viewModelScope.launch {
            _uiEffect.send(WitnessReportUiEffect.NavigateToAddressSearch)
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

    private fun openCamera() {
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
                isSuccessDialogShown = false,
                isAppSettingDialogShown = false
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