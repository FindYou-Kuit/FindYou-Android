package com.example.findu.presentation.ui.report

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
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
) : ViewModel() {
    private val getBreedDataUseCase: GetBreedDataUseCase
    private val analysisImageWithGptUseCase: AnalysisImageWithGptUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
  
    private val _gptData: MutableStateFlow<GptData> = MutableStateFlow(GptData())
    val gptData = _gptData.asStateFlow()

    private val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()
    
    private val _breedData = MutableStateFlow<BreedData?>(null)
    val breedData: StateFlow<BreedData?> = _breedData

    private val _errorMessage = MutableStateFlow<String?>(null)  // 오류 메시지 관리
    val errorMessage = _errorMessage.asStateFlow()

    private val _speciesType = MutableStateFlow(SpeciesType.ETC)

    private val _selectedBreedNames = MutableStateFlow<List<String>>(emptyList())
    val selectedBreedNames: StateFlow<List<String>> = _selectedBreedNames

    init {
        getBreedData()
    }

    private fun getBreedData() {
        viewModelScope.launch {
            getBreedDataUseCase().fold(
                onSuccess = { data ->
                    _breedData.value = data
                    _selectedBreedNames.value = data.etcBreedList.map { it.breedName }
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
            SpeciesType.DOG -> _selectedBreedNames.value =
                _breedData.value?.dogBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.CAT -> _selectedBreedNames.value =
                _breedData.value?.catBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.ETC -> _selectedBreedNames.value =
                _breedData.value?.etcBreedList?.map { it.breedName } ?: emptyList()
        }
    }

    fun getGptData(imageUri: Uri) {
        viewModelScope.launch {
            imageUri.uriToBase64(context)?.let { encodeString ->
                analysisImageWithGptUseCase(encodeString).fold(
                    onSuccess = { value ->
                        _gptData.value = value
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message.toString()
                    })
            } ?: run {
                _errorMessage.value = "Failed to convert image to base64"
            }
        }
    }
}