package com.example.findu.presentation.ui.report.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.usecase.GetBreedValidationUseCase
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
import com.example.findu.domain.usecase.report.UploadImagesUseCase
import com.example.findu.presentation.model.GptUiState
import com.example.findu.presentation.util.UriUtil.toMultiPartBodys
import com.example.findu.presentation.util.UriUtil.uriToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getBreedDataUseCase: GetBreedDataUseCase,
    private val analysisImageWithGptUseCase: AnalysisImageWithGptUseCase,
    private val getBreedValidationUseCase: GetBreedValidationUseCase,
    private val uploadImagesUseCase: UploadImagesUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _gptData: MutableStateFlow<GptData> = MutableStateFlow(GptData())
    val gptData = _gptData.asStateFlow()

    private val _breedData = MutableStateFlow<BreedData?>(null)
    val breedData: StateFlow<BreedData?> = _breedData

    private val _speciesType = MutableStateFlow(SpeciesType.ETC)

    private val _selectedBreedList = MutableStateFlow<List<String>>(emptyList())
    val selectedBreedList: StateFlow<List<String>> = _selectedBreedList

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _gptUiState: MutableStateFlow<GptUiState> = MutableStateFlow(GptUiState.Default)
    val gptUiState = _gptUiState.asStateFlow()

    private val _selectedFeatureIds = MutableStateFlow<MutableList<Int>>(mutableListOf())

    init {
        getBreedData()
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

    fun selectSpeciesType(speciesType: SpeciesType) {
        _speciesType.value = speciesType
        when (speciesType) {
            SpeciesType.DOG -> _selectedBreedList.value =
                _breedData.value?.dogBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.CAT -> _selectedBreedList.value =
                _breedData.value?.catBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.ETC -> _selectedBreedList.value =
                _breedData.value?.etcBreedList?.map { it.breedName } ?: emptyList()
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

    fun uploadImages(images: List<Uri>) {

        viewModelScope.launch {
            uploadImagesUseCase(
                images.toMultiPartBodys(context)
            ).fold(
                onSuccess = { data ->
//                    _errorMessage.value = data.message
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "이미지 업로드 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun updateSelectedFeatureIds(featureId: Int) {
        if (_selectedFeatureIds.value.contains(featureId)) {
            _selectedFeatureIds.value.remove(featureId)
        } else {
            _selectedFeatureIds.value.add(featureId)
        }
    }

}