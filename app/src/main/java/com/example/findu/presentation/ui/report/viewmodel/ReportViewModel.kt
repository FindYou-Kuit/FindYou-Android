package com.example.findu.presentation.ui.report.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.SexType
import com.example.findu.domain.model.report.WitnessReportData
import com.example.findu.domain.usecase.GetBreedValidationUseCase
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
import com.example.findu.domain.usecase.report.PostMissingReportUseCase
import com.example.findu.domain.usecase.report.PostWitnessReportUseCase
import com.example.findu.domain.usecase.report.UploadImagesUseCase
import com.example.findu.presentation.ui.report.model.GptUiState
import com.example.findu.presentation.ui.report.model.ReportUiState
import com.example.findu.presentation.util.UriUtil.toMultiPartBodys
import com.example.findu.presentation.util.UriUtil.uriToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getBreedDataUseCase: GetBreedDataUseCase,
    private val analysisImageWithGptUseCase: AnalysisImageWithGptUseCase,
    private val getBreedValidationUseCase: GetBreedValidationUseCase,
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val postMissingReportUseCase: PostMissingReportUseCase,
    private val postWitnessReportUseCase: PostWitnessReportUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _gptData: MutableStateFlow<GptData?> = MutableStateFlow(null)
    val gptData = _gptData.asStateFlow()

    private val _breedData = MutableStateFlow<BreedData?>(null)
    val breedData: StateFlow<BreedData?> = _breedData

    private val _selectedBreedList = MutableStateFlow<List<String>>(emptyList())
    val selectedBreedList: StateFlow<List<String>> = _selectedBreedList


    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _gptUiState: MutableStateFlow<GptUiState> = MutableStateFlow(GptUiState.Default)
    val gptUiState = _gptUiState.asStateFlow()

    private val _reportUiState = MutableStateFlow<ReportUiState>(ReportUiState.Default)
    val reportUiState = _reportUiState.asStateFlow()

//    private val _imageUris = MutableStateFlow<List<Uri>>(emptyList())
//    private val _selectedSpeciesType = MutableStateFlow<SpeciesType?>(null)
//    private val _selectedBreedName = MutableStateFlow<String?>(null)
//    private val _selectedSexType: MutableStateFlow<SexType?> = MutableStateFlow(null)
//    private val _selectedFurColors = MutableStateFlow<List<FurColorType>>(emptyList())
//    private val _selectedFeatureIds = MutableStateFlow<MutableList<Int>>(mutableListOf())
//    private val _description: String? = null
//    private val _location = MutableStateFlow<String?>(null)
//    private val _selectedMissingDate = MutableStateFlow<Date?>(null)

    private val _imageUris = MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val _selectedSpeciesType = MutableStateFlow<SpeciesType?>(null)
    private val _selectedBreedName = MutableStateFlow<String?>(null)
    private val _selectedSexType = MutableStateFlow<SexType?>(null)
    private val _selectedFurColors = MutableStateFlow<MutableList<FurColorType>>(mutableListOf())
    private val _selectedFeatureIds = MutableStateFlow<MutableList<Int>>(mutableListOf())
    private val _description = MutableStateFlow<String?>(null)
    private val _location = MutableStateFlow<String?>(null)
    private val _selectedMissingDate = MutableStateFlow<Date?>(null)

    private var imageUrls = listOf<String>()

    init {
        getBreedData()
//        testPostMissingReport()
    }

    private fun getBreedData() {
        viewModelScope.launch {
            getBreedDataUseCase().fold(
                onSuccess = { data ->
                    _breedData.value = data
                    _selectedBreedList.value = data.etcBreedList.map { it.breedName }
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    private fun getBreedValidation(gptValue: GptData) {
        viewModelScope.launch {
            getBreedValidationUseCase(gptValue.breed).fold(
                onSuccess = { validationData ->
                    if (validationData.isExist) {
                        _gptData.value = gptValue
                    } else {
                        _gptData.value = gptValue.copy(breed = "")
                        _errorMessage.value = "유효하지 않은 품종입니다."
                    }
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "품종을 검증하는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun getGptData(imageUri: Uri) {
        viewModelScope.launch {
            _gptUiState.value = GptUiState.Loading
            imageUri.uriToBase64(context)?.let { encodeString ->
                analysisImageWithGptUseCase(encodeString).fold(
                    onSuccess = { value ->
                        getBreedValidation(value)
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message.toString()
                    })

                _gptUiState.value = GptUiState.Finished
            } ?: run {
                _errorMessage.value = "Failed to convert image to base64"
            }
        }
    }

    fun updateReportData(
        imageUris: Uri? = null,
        speciesType: SpeciesType? = null,
        breedName: String? = null,
        sexType: SexType? = null,
        furColor: FurColorType? = null,
        featureIds: Int? = null,
        location: String? = null,
        missingDate: Date? = null
    ) {
        imageUris?.let { _imageUris.value.add(it) }
        speciesType?.let { selectSpeciesType(it) }
        breedName?.let { _selectedBreedName.value = it }
        sexType?.let { _selectedSexType.value = it }
        furColor?.let { updateSelectedFurColors(it) }
        featureIds?.let { updateSelectedFeatureIds(it) }
        location?.let { _location.value = it }
        missingDate?.let { _selectedMissingDate.value = it }

        Log.d("ReportViewModel", "_selectedSpeciesType: ${_selectedSpeciesType.value}")
        Log.d("ReportViewModel", "_selectedBreedName: ${_selectedBreedName.value}")
        Log.d("ReportViewModel", "_selectedSexType: ${_selectedSexType.value}")
        Log.d("ReportViewModel", "_selectedFurColors: ${_selectedFurColors.value}")
        Log.d("ReportViewModel", "_selectedFeatureIds: ${_selectedFeatureIds.value}")
        Log.d("ReportViewModel", "_location: ${_location.value}")
        Log.d("ReportViewModel", "_selectedMissingDate: ${_selectedMissingDate.value}")

        if (_selectedSpeciesType.value != null &&
            !_selectedBreedName.value.isNullOrEmpty() &&
            _selectedSexType.value != null &&
            _selectedFurColors.value.isNotEmpty() &&
            _selectedFeatureIds.value.isNotEmpty() &&
            !_location.value.isNullOrEmpty() &&
            _selectedMissingDate.value != null
        ) {
            _reportUiState.value = ReportUiState.Enable
        }
    }

    fun testPostMissingReport(
    ) {
        val missingReportData =
            MissingReportData(
                imageUrls = listOf(
                    "https://findyoubucket.s3.ap-northeast-2.amazonaws.com/0b8780d6-59ba-4bba-99d1-3715ea08446b.jpg",
                    "https://findyoubucket.s3.ap-northeast-2.amazonaws.com/5b947ba4-b5f4-4588-8ae3-0c324fee3a66.jpg"
                ),
                breedId = 1,
                sex = SexType.MALE,
                furColors = listOf(
                    FurColorType.GRAY, FurColorType.WHITE, FurColorType.BLACK, FurColorType.BROWN
                ),
                location = "서울특별시 광진구 구의동 649-4",
                featureIds = listOf(
                    1, 2, 3, 4, 5, 6
                ),
                description = "테스트용 설명입니다.",
                missingDate = Clock.System.now()
            )

        viewModelScope.launch {
            _reportUiState.value = ReportUiState.Loading
//            uploadImages(images)

            postMissingReportUseCase(missingReportData).fold(
                onSuccess = { },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "신고 접수 중 오류가 발생했습니다."
                    _reportUiState.value = ReportUiState.Error
                }
            )
            _reportUiState.value = ReportUiState.Finished
        }
    }

    fun postMissingReport(
        description: String
    ) {
        val breedId = getBreedIds(_selectedBreedName.value!!)

        viewModelScope.launch {
            _reportUiState.value = ReportUiState.Loading

            uploadImages(_imageUris.value)

            val missingReportData =
                MissingReportData(
                    imageUrls = imageUrls,
                    breedId = breedId,
                    sex = _selectedSexType.value!!,
                    furColors = _selectedFurColors.value,
                    location = _location.value!!,
                    featureIds = _selectedFeatureIds.value,
                    description = description,
                    missingDate = Instant.fromEpochMilliseconds(_selectedMissingDate.value!!.time)
                )

            postMissingReportUseCase(missingReportData).fold(
                onSuccess = { },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "신고 접수 중 오류가 발생했습니다."
                    _reportUiState.value = ReportUiState.Error
                }
            )
            _reportUiState.value = ReportUiState.Finished
        }
    }

    fun postWitnessReport(
        description: String
    ) {
        val breedId = getBreedIds(_selectedBreedName.value!!)

        viewModelScope.launch {
            _reportUiState.value = ReportUiState.Loading

            uploadImages(_imageUris.value)

            val witnessReportData =
                WitnessReportData(
                    imageUrls = imageUrls,
                    breedId = breedId,
                    furColors = _selectedFurColors.value,
                    location = _location.value!!,
                    featureIds = _selectedFeatureIds.value,
                    description = description,
                    missingDate = Instant.fromEpochMilliseconds(_selectedMissingDate.value!!.time)
                )

            postWitnessReportUseCase(witnessReportData).fold(
                onSuccess = { },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "신고 접수 중 오류가 발생했습니다."
                    _reportUiState.value = ReportUiState.Error
                }
            )

        }
    }

    private fun uploadImages(images: List<Uri>) {

        viewModelScope.launch {
            uploadImagesUseCase(
                images.toMultiPartBodys(context)
            ).fold(
                onSuccess = { data ->
                    imageUrls = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "이미지 업로드 중 오류가 발생했습니다."
                }
            )
        }
    }

    private fun getBreedIds(breedName: String): Int =
        when (_selectedSpeciesType.value) {
            SpeciesType.DOG -> {
                _breedData.value?.dogBreedList?.find { it.breedName == breedName }!!.breedId
            }

            SpeciesType.CAT -> {
                _breedData.value?.catBreedList?.find { it.breedName == breedName }!!.breedId
            }

            SpeciesType.ETC, null -> {
                _breedData.value?.etcBreedList?.find { it.breedName == breedName }!!.breedId
            }
        }

    private fun selectSpeciesType(speciesType: SpeciesType) {
        _selectedSpeciesType.value = speciesType
        when (speciesType) {
            SpeciesType.DOG -> _selectedBreedList.value =
                _breedData.value?.dogBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.CAT -> _selectedBreedList.value =
                _breedData.value?.catBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.ETC -> _selectedBreedList.value =
                _breedData.value?.etcBreedList?.map { it.breedName } ?: emptyList()
        }
        updateReportData(breedName = null)
    }

    private fun updateSelectedFurColors(furColor: FurColorType) {
        if (_selectedFurColors.value.contains(furColor)) {
            _selectedFurColors.value.remove(furColor)
        } else {
            _selectedFurColors.value.add(furColor)
        }
    }

    private fun updateSelectedFeatureIds(featureId: Int) {
        if (_selectedFeatureIds.value.contains(featureId)) {
            _selectedFeatureIds.value.remove(featureId)
        } else {
            _selectedFeatureIds.value.add(featureId)
        }
    }
}