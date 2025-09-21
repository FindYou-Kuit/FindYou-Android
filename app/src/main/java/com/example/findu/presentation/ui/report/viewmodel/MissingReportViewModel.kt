package com.example.findu.presentation.ui.report.viewmodel

import android.net.Uri
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.model.report.Gender
import com.example.findu.domain.usecase.GetBreedDataUseCase
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

data class MissingReportUiState(
    val isFirstPermissionRequest: Boolean = true,
    val imageUriList: List<Uri> = emptyList(),
    val speciesType: SpeciesType = SpeciesType.DOG,
    val breedSearchText: TextFieldState = TextFieldState(),
    val breed: Breed? = null,
    val breedList: BreedData = BreedData(),
    val showingBreedList: List<Breed> = emptyList(),
    val age: TextFieldState = TextFieldState(),
    val gender: Gender = Gender.MALE,
    val rfidNumber: TextFieldState = TextFieldState(),
    val selectedFurColors: List<FurColorType> = emptyList(),
    val nowDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val missingDate: String = "",
    val isDateBottomSheetShown: Boolean = false,
    val description: TextFieldState = TextFieldState(),
    val address: String = "",
    val currentLatLng: LatLng? = null,
    val nearPlace: TextFieldState = TextFieldState(),
    val isImageDialogShown: Boolean = false,
    val isSuccessDialogShown: Boolean = false,
    val isAppSettingDialogShown: Boolean = false,
)

sealed class MissingReportUiEvent {
    data object OnBackPressed : MissingReportUiEvent()
    data object OnAddImageClick : MissingReportUiEvent()
    data object OnOpenCameraClick : MissingReportUiEvent()
    data object OnOpenGalleryClick : MissingReportUiEvent()
    data object OnSelectAnimalInfoClick : MissingReportUiEvent()
    data class OnImageSelected(val uri: Uri) : MissingReportUiEvent()
    data class OnSpeciesClick(val speciesType: SpeciesType) : MissingReportUiEvent()
    data object OnSearchFieldChange : MissingReportUiEvent()
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
    data object OnDismissDialog : MissingReportUiEvent()
    data object OnReportFinishButtonClick : MissingReportUiEvent()
    data object OnDismissKeyboard : MissingReportUiEvent()
    data object OnAppSettingClick : MissingReportUiEvent()
    data object ClearFocus : MissingReportUiEvent()
}

sealed class MissingReportUiEffect {
    data object NavigateToUp : MissingReportUiEffect()
    data object NavigateToAnimalInfo : MissingReportUiEffect()
    data object NavigateToAddressSearch : MissingReportUiEffect()
    data class ShowToast(val message: String) : MissingReportUiEffect()
    data object ShowFinishDialog : MissingReportUiEffect()
    data object DismissKeyboard : MissingReportUiEffect()
    data object ClearFocus : MissingReportUiEffect()
    data object OpenCamera : MissingReportUiEffect()
    data object OpenGallery : MissingReportUiEffect()
    data object OpenAppSettings : MissingReportUiEffect()
}

@HiltViewModel
class MissingReportViewModel @Inject constructor(
    private val getBreedDataUseCase: GetBreedDataUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MissingReportUiState())
    val uiState: StateFlow<MissingReportUiState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<MissingReportUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        fetchBreedList()
    }

    private fun fetchBreedList() {
        // TODO: 실제 API 연동 필요
        viewModelScope.launch {
            getBreedDataUseCase().fold(
                onSuccess = { breedList ->
                    _uiState.update { it.copy(breedList = breedList) }
                },
                onFailure = { error ->
                    _uiEffect.send(
                        MissingReportUiEffect.ShowToast(
                            message = error.message ?: "품종 데이터를 불러오지 못했습니다.",
                        )
                    )
                }
            )
        }
//        _uiState.update {
//            it.copy(
//                breedList = listOf(
//                    Breed.DogBreed(1, "Labrador Retriever", SpeciesType.DOG),
//                    Breed.DogBreed(2, "German Shepherd", SpeciesType.DOG),
//                    Breed.DogBreed(3, "Golden Retriever", SpeciesType.DOG),
//                    Breed.DogBreed(4, "Bulldog", SpeciesType.DOG),
//                    Breed.DogBreed(5, "Beagle", SpeciesType.DOG),
//                    Breed.DogBreed(6, "Poodle", SpeciesType.DOG),
//                )
//            )
//        }
    }

    fun handleEvent(event: MissingReportUiEvent) {
        when (event) {
            MissingReportUiEvent.OnBackPressed -> navigateUp()
            MissingReportUiEvent.OnAddImageClick -> setImageDialogVisible()
            MissingReportUiEvent.OnAddressSearchClick -> navigateToAddressSearch()
            is MissingReportUiEvent.OnAddressUpdated -> updateAddress(event.address)
            is MissingReportUiEvent.OnBreedClick -> updateBreed(event.breed)
            is MissingReportUiEvent.OnSearchFieldChange -> updateBreedResult()
            MissingReportUiEvent.OnMissingDateClicked -> setDateBottomSheetVisible(true)
            is MissingReportUiEvent.OnDateSelected -> updateDate(event.dateTime)
            is MissingReportUiEvent.OnFurColorSelected ->
                updateFurColor(event.furColorType, event.flag)

            is MissingReportUiEvent.OnGenderSelected -> updateGender(event.gender)
            MissingReportUiEvent.OnInfoFinishButtonClick -> navigateUp()
            is MissingReportUiEvent.OnMapPinMoved -> updateAddress(event.latLng)
            MissingReportUiEvent.OnDismissDialog -> setDialogInVisible()
            MissingReportUiEvent.OnOpenCameraClick -> openCamera()
            MissingReportUiEvent.OnOpenGalleryClick -> openGallery()
            MissingReportUiEvent.OnReportFinishButtonClick -> postMissingReport()
            MissingReportUiEvent.OnSelectAnimalInfoClick -> navigateToAnimalInfo()
            is MissingReportUiEvent.OnSpeciesClick -> updateSpecies(event.speciesType)
            is MissingReportUiEvent.OnImageSelected -> addImageToList(event.uri)
            MissingReportUiEvent.OnDismissKeyboard -> dismissKeyboard()
            MissingReportUiEvent.OnAppSettingClick -> openAppSettings()
            MissingReportUiEvent.ClearFocus -> clearViewFocus()
        }
    }

    private fun updateBreedResult() {
        val showingBreedList = when (_uiState.value.speciesType) {
            SpeciesType.DOG -> _uiState.value.breedList.dogBreedList
            SpeciesType.CAT -> _uiState.value.breedList.catBreedList
            SpeciesType.ETC -> _uiState.value.breedList.etcBreedList
        }.filter {
            if (_uiState.value.breedSearchText.text.isBlank()) true
            else it.name.contains(_uiState.value.breedSearchText.text, ignoreCase = true)
        }
        _uiState.update {
            it.copy(showingBreedList = showingBreedList)
        }
    }

    private fun postMissingReport() {
        // TODO : 신고 등록 API 구현
        val normalizedAddress = _uiState.value.address.toNormalizeAddress()
        showFinishDialog()
    }

    private fun clearViewFocus() {
        viewModelScope.launch {
            _uiEffect.send(MissingReportUiEffect.ClearFocus)
        }
    }

    private fun updateGender(gender: Gender) {
        _uiState.update { it.copy(gender = gender) }
    }

    private fun dismissKeyboard() {
        viewModelScope.launch {
            _uiEffect.send(MissingReportUiEffect.DismissKeyboard)
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
            _uiEffect.send(MissingReportUiEffect.NavigateToAnimalInfo)
        }
    }

    private fun updateDate(dateTime: LocalDateTime) {
        _uiState.update {
            it.copy(
                missingDate = "${dateTime.year}년 ${dateTime.monthNumber}월 ${dateTime.dayOfMonth}일",
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
            _uiEffect.send(MissingReportUiEffect.NavigateToAddressSearch)
        }
    }

    private fun showFinishDialog() {
        viewModelScope.launch {
            _uiEffect.send(MissingReportUiEffect.ShowFinishDialog)
        }
    }

    private fun navigateUp() {
        viewModelScope.launch {
            _uiEffect.send(MissingReportUiEffect.NavigateToUp)
        }
    }

    private fun openAppSettings() {
        viewModelScope.launch {
            _uiEffect.send(MissingReportUiEffect.OpenAppSettings)
        }
    }

    private fun openGallery() {
        viewModelScope.launch {
            _uiEffect.send(MissingReportUiEffect.OpenGallery)
        }
    }

    private fun openCamera() {
        viewModelScope.launch {
            _uiEffect.send(MissingReportUiEffect.OpenCamera)
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