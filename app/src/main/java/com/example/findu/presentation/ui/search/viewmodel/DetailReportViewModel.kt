package com.example.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.search.DetailMissingData
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailWitnessData
import com.example.findu.domain.usecase.GetDetailSearchUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestReportAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestReportAnimalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailReportViewModel @Inject constructor(
    private val getDetailSearchUseCase: GetDetailSearchUseCase,
    private val postInterestReportAnimalUseCase: PostInterestReportAnimalUseCase,
    private val deleteInterestReportAnimalUseCase: DeleteInterestReportAnimalUseCase
) : ViewModel() {

    private val _detailMissingData = MutableStateFlow<DetailMissingData?>(null)
    val detailMissingData = _detailMissingData.asStateFlow()

    private val _detailWitnessData = MutableStateFlow<DetailWitnessData?>(null)
    val detailWitnessData = _detailWitnessData.asStateFlow()

    private val _detailProtectData = MutableStateFlow<DetailProtectData?>(null)
    val detailProtectData = _detailProtectData.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun getDetailSearchReport(reportId: Long) {
        viewModelScope.launch {
            getDetailSearchUseCase.getReportData(reportId).fold(
                onSuccess = { data ->
                    _detailSearchData.value = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "신고 동물 상세 정보를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun setInterestReportAnimal(id: Long) {
        viewModelScope.launch {
            if (_detailSearchData.value?.interest == true) {
                postInterestReportAnimalUseCase(id).fold(
                    onSuccess = { },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "신고 동물 관심 등록 중 오류가 발생했습니다."
                    }
                )
            } else {
                deleteInterestReportAnimalUseCase(id).fold(
                    onSuccess = { },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "신고 동물 관심 해제 중 오류가 발생했습니다."
                    }
                )
            }
        }
    }
}