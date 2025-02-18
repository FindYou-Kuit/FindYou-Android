package com.example.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailReportData
import com.example.findu.domain.usecase.GetDetailSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailReportViewModel @Inject constructor(
    private val getDetailSearchUseCase: GetDetailSearchUseCase
) : ViewModel() {

    private val _detailSearchData = MutableStateFlow<DetailReportData?>(null)
    val detailSearchData = _detailSearchData.asStateFlow()

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
}