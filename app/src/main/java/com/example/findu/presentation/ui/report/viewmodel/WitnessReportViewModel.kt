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
import com.example.findu.domain.model.breed.SpeciesType.Companion.fromString
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.model.report.WitnessReportData
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.PostAiDetectionUseCase
import com.example.findu.domain.usecase.report.PostWitnessReportUseCase
import com.example.findu.domain.usecase.report.UploadImagesUseCase
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.util.UriUtil.toMultiPartBodys
import com.example.findu.presentation.util.UriUtil.uriToBase64
import com.example.findu.presentation.util.extension.toDateString
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

data class WitnessReportUiState(
    val loadState: LoadState = LoadState.Idle,
    val isFirstPermissionRequest: Boolean = true,
    val imageUriList: List<Uri> = emptyList(),
    val speciesType: SpeciesType = SpeciesType.DOG,
    val breedSearchText: TextFieldState = TextFieldState(),
    val breed: Breed? = null,
    val breedList: BreedData = BreedData(),
    val showingBreedList: List<Breed> = emptyList(),
    val selectedFurColors: List<FurColorType> = emptyList(),
    val nowDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val witnessDate: String = "",
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

sealed class WitnessReportUiEvent {
    data object OnBackPressed : WitnessReportUiEvent()
    data class OnAddImageClick(val page: Int) : WitnessReportUiEvent()
    data class OnRemoveImageClick(val uri: Uri) : WitnessReportUiEvent()
    data object OnOpenCameraClick : WitnessReportUiEvent()
    data object OnOpenGalleryClick : WitnessReportUiEvent()
    data class OnImageSelected(val uri: Uri) : WitnessReportUiEvent()
    data class OnAIDetectionClick(val uri: Uri) : WitnessReportUiEvent()
    data object OnSelectAnimalInfoClick : WitnessReportUiEvent()
    data class OnSpeciesClick(val speciesType: SpeciesType) : WitnessReportUiEvent()
    data class OnBreedClick(val breed: Breed) : WitnessReportUiEvent()
    data object OnSearchFieldChange : WitnessReportUiEvent()
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
class WitnessReportViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getBreedDataUseCase: GetBreedDataUseCase,
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val postWitnessReportUseCase: PostWitnessReportUseCase,
    private val postAiDetectionUseCase: PostAiDetectionUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(WitnessReportUiState())
    val uiState: StateFlow<WitnessReportUiState>
        get() = _uiState.asStateFlow()

    private val _uiEffect = Channel<WitnessReportUiEffect>()
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
                        WitnessReportUiEffect.ShowToast(
                            message = error.message ?: "품종 데이터를 불러오지 못했습니다.",
                        )
                    )
                    _uiState.update { it.copy(loadState = LoadState.Error) }
                }
            )
        }
    }

    fun handleEvent(event: WitnessReportUiEvent) {
        when (event) {
            WitnessReportUiEvent.OnBackPressed -> navigateUp()
            is WitnessReportUiEvent.OnAddImageClick -> setImageDialogVisible(event.page)
            is WitnessReportUiEvent.OnRemoveImageClick -> deleteImage(event.uri)
            is WitnessReportUiEvent.OnAIDetectionClick -> detectionWithAI(event.uri)
            WitnessReportUiEvent.OnAddressSearchClick -> navigateToAddressSearch()
            is WitnessReportUiEvent.OnAddressUpdated -> updateAddress(event.address)
            is WitnessReportUiEvent.OnBreedClick -> updateBreed(event.breed)
            WitnessReportUiEvent.OnSearchFieldChange -> updateBreedResult()
            WitnessReportUiEvent.OnWitnessDateClicked -> setDateBottomSheetVisible(true)
            is WitnessReportUiEvent.OnDateSelected -> updateDate(event.dateTime)
            is WitnessReportUiEvent.OnFurColorSelected ->
                updateFurColor(event.furColorType, event.flag)

            WitnessReportUiEvent.OnInfoFinishButtonClick -> navigateUp()
            is WitnessReportUiEvent.OnMapPinMoved -> updateAddress(event.latLng)
            WitnessReportUiEvent.OnDismissDialog -> setDialogInVisible()
            WitnessReportUiEvent.OnOpenCameraClick -> openCamera()
            WitnessReportUiEvent.OnOpenGalleryClick -> openGallery()
            WitnessReportUiEvent.OnReportFinishButtonClick -> postWitnessReport()
            WitnessReportUiEvent.OnSelectAnimalInfoClick -> navigateToAnimalInfo()
            is WitnessReportUiEvent.OnSpeciesClick -> updateSpecies(event.speciesType)
            is WitnessReportUiEvent.OnImageSelected -> addImageToList(event.uri)
            WitnessReportUiEvent.OnDismissKeyboard -> dismissKeyboard()
            WitnessReportUiEvent.OnAppSettingClick -> openAppSettings()
            WitnessReportUiEvent.ClearFocus -> clearFocus()
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

    private fun postWitnessReport() {
        _uiState.update { it.copy(loadState = LoadState.Loading) }
        viewModelScope.launch {
            val imageUrls = getImageUrls()

            val witnessReportData = WitnessReportData(
                imageUrls = imageUrls,
                species = _uiState.value.speciesType,
                breed = _uiState.value.breed!!.name,
                furColors = _uiState.value.selectedFurColors,
                location = _uiState.value.address.toNormalizeAddress(),
                description = _uiState.value.description.text.toString(),
                foundDate = _uiState.value.witnessDate.toDateString(),
                landmark = _uiState.value.nearPlace.text.toString(),
            )
            postWitnessReportUseCase(
                witnessReportData = witnessReportData,
            ).fold(
                onSuccess = {
                    showFinishDialog()
                    _uiState.update { it.copy(loadState = LoadState.Success) }
                },
                onFailure = { error ->
                    _uiEffect.send(
                        WitnessReportUiEffect.ShowToast(
                            message = error.message ?: "목격 신고 등록에 실패했습니다.",
                        )
                    )
                    _uiState.update { it.copy(loadState = LoadState.Error) }
                    Log.d("http", "Error Message: : $error")
                }
            )
        }
    }

    private suspend fun getImageUrls(): List<String> {
        val uriFiles = _uiState.value.imageUriList.toMultiPartBodys(context)
        return uploadImagesUseCase(uriFiles).fold(
            onSuccess = { imageUrls -> imageUrls },
            onFailure = { error ->
                viewModelScope.launch {
                    _uiEffect.send(
                        WitnessReportUiEffect.ShowToast(
                            message = error.message ?: "이미지 업로드에 실패했습니다.",
                        )
                    )
                    Log.d("http", "Error Message: : $error")
                }
                emptyList()
            }
        )
    }

    private fun detectionWithAI(uri: Uri) {
        _uiState.update { it.copy(loadState = LoadState.Loading) }
        val base64Image = uri.uriToBase64(context)
        viewModelScope.launch {
            postAiDetectionUseCase(imageUrl = base64Image).fold(
                onSuccess = { aiDetectionData ->
                    val detectedSpecies = fromString(aiDetectionData.species)
                    val detectedBreedName = getDetectedBreedName(
                        detectedName = aiDetectionData.breed,
                        speciesType = detectedSpecies,
                    )
                    val detectedFurColors =
                        aiDetectionData.furColors.map { FurColorType.fromString(it) }

                    _uiState.update {
                        it.copy(
                            breedSearchText = TextFieldState(detectedBreedName.name),
                            speciesType = detectedSpecies,
                            breed = detectedBreedName,
                            selectedFurColors = detectedFurColors,
                            loadState = LoadState.Success,
                        )
                    }

                    _uiEffect.send(
                        WitnessReportUiEffect.ShowToast(
                            message = "AI 분석이 완료되었습니다!",
                        )
                    )
                },
                onFailure = { error ->
                    _uiEffect.send(
                        WitnessReportUiEffect.ShowToast(
                            message = error.message ?: "AI 분석에 실패했습니다.",
                        )
                    )
                    _uiState.update { it.copy(loadState = LoadState.Error) }
                    Log.d("http", "Error Message: : $error")
                }
            )
        }
    }

    private fun getDetectedBreedName(detectedName: String, speciesType: SpeciesType): Breed =
        when (speciesType) {
            SpeciesType.DOG -> Breed.DogBreed(
                breedName = _uiState.value.breedList.dogBreedList.find {
                    it.name.contains(detectedName)
                }?.breedName ?: AI_DETECT_FAIL_TEXT,
                species = SpeciesType.DOG
            )

            SpeciesType.CAT -> Breed.CatBreed(
                breedName = _uiState.value.breedList.catBreedList.find {
                    it.name.contains(detectedName)
                }?.breedName ?: AI_DETECT_FAIL_TEXT,
                species = SpeciesType.CAT
            )

            SpeciesType.ETC -> Breed.EtcBreed(
                breedName = _uiState.value.breedList.etcBreedList.find {
                    it.name.contains(detectedName)
                }?.breedName ?: AI_DETECT_FAIL_TEXT,
                species = SpeciesType.ETC
            )
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

    private companion object {
        const val AI_DETECT_FAIL_TEXT = "품종 인식에 실패했습니다."
    }
}