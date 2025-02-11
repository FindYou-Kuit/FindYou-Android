package com.example.findu.presentation.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.usecase.GetBreedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getBreedDataUseCase: GetBreedDataUseCase
) : ViewModel() {
    private val _breedData = MutableStateFlow(BreedData())
    val breedData: StateFlow<BreedData> = _breedData

    private val _errorMessage = MutableStateFlow<String?>(null)  // 오류 메시지 관리
    val errorMessage = _errorMessage.asStateFlow()

    init {
        getBreedData()
    }

    private fun getBreedData() {
        viewModelScope.launch {
            getBreedDataUseCase().fold(
                onSuccess = { data ->
                    _breedData.value = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

}