package com.example.findu.presentation.ui.report.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.model.report.Gender
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.report.PostMissingReportUseCase
import com.example.findu.domain.usecase.report.UploadImagesUseCase
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.util.UriUtil.toMultiPartBodys
import com.example.findu.presentation.util.extension.toNormalizeAddress
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    val loadState: LoadState = LoadState.Idle,
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
    val addingPageIndex: Int = 0,
    val isImageDialogShown: Boolean = false,
    val isSuccessDialogShown: Boolean = false,
    val isAppSettingDialogShown: Boolean = false,
)

sealed class MissingReportUiEvent {
    data object OnBackPressed : MissingReportUiEvent()
    data class OnAddImageClick(val page: Int) : MissingReportUiEvent()
    data class OnRemoveImageClick(val uri: Uri) : MissingReportUiEvent()
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
    @ApplicationContext private val context: Context,
    private val getBreedDataUseCase: GetBreedDataUseCase,
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val postMissingReportUseCase: PostMissingReportUseCase,
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
        _uiState.update { it.copy(loadState = LoadState.Loading) }
        viewModelScope.launch {
            getBreedDataUseCase().fold(
                onSuccess = { breedList ->
                    _uiState.update {
                        it.copy(
                            breedList = breedList,
                            loadState = LoadState.Success,
                        )
                    }
                },
                onFailure = { error ->
                    _uiEffect.send(
                        MissingReportUiEffect.ShowToast(
                            message = error.message ?: "품종 데이터를 불러오지 못했습니다.",
                        )
                    )
                    _uiState.update { it.copy(loadState = LoadState.Error) }
                }
            )
        }
    }

    fun handleEvent(event: MissingReportUiEvent) {
        when (event) {
            MissingReportUiEvent.OnBackPressed -> navigateUp()
            is MissingReportUiEvent.OnAddImageClick -> setImageDialogVisible(event.page)
            is MissingReportUiEvent.OnRemoveImageClick -> deleteImage(event.uri)
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
        _uiState.update { it.copy(loadState = LoadState.Loading) }
        viewModelScope.launch {
            val imageUrls = getImageUrls()

            val missingReportData = MissingReportData(
                imageUrls = imageUrls,
                species = _uiState.value.speciesType,
                breed = _uiState.value.breed?.name.orEmpty(),
                age = "${_uiState.value.age.text}살",
                sex = _uiState.value.gender,
                rfid = _uiState.value.rfidNumber.text.toString(),
                furColors = _uiState.value.selectedFurColors,
                missingDate = _uiState.value.missingDate,
                location = _uiState.value.address.toNormalizeAddress(),
                landmark = _uiState.value.nearPlace.text.toString(),
                description = _uiState.value.description.text.toString(),
            )
            postMissingReportUseCase(
                missingReportData = missingReportData
            ).fold(
                onSuccess = {
                    showFinishDialog()
                    _uiState.update { it.copy(loadState = LoadState.Success) }
                },
                onFailure = { error ->
                    _uiEffect.send(
                        MissingReportUiEffect.ShowToast(
                            message = error.message ?: "목격 신고 등록에 실패했습니다.",
                        )
                    )
                    _uiState.update { it.copy(loadState = LoadState.Error) }
                    Log.d("http", "Error Message: : $error")
                }
            )
        }

        showFinishDialog()
    }

    private suspend fun getImageUrls(): List<String> {
        val uriFiles = _uiState.value.imageUriList.toMultiPartBodys(context)
        return uploadImagesUseCase(uriFiles).fold(
            onSuccess = { imageUrls ->
                imageUrls
            },
            onFailure = { error ->
                viewModelScope.launch {
                    _uiEffect.send(
                        MissingReportUiEffect.ShowToast(
                            message = error.message ?: "이미지 업로드에 실패했습니다.",
                        )
                    )
                    Log.d("http", "Error Message: : $error")
                }
                emptyList()
            }
        )
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


    private fun setImageDialogVisible(page: Int) {
        _uiState.update {
            it.copy(
                isImageDialogShown = true,
                addingPageIndex = page,
            )
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
        _uiState.update {
            val uriList = it.imageUriList.toMutableList().apply {
                add(it.addingPageIndex, uri)
            }
            it.copy(
                imageUriList = uriList,
                isImageDialogShown = false,
                addingPageIndex = 0,
            )
        }
    }

    private fun deleteImage(uri: Uri) {
        _uiState.update {
            val uriList = it.imageUriList.filterNot { imageUri -> imageUri == uri }
            it.copy(
                imageUriList = uriList,
            )
        }
    }
}