package com.example.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.usecase.GetDetailSearchUseCase
import com.example.findu.domain.usecase.interest.PostInterestProtectingAnimalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailSearchViewModel @Inject constructor(
    private val getDetailSearchUseCase: GetDetailSearchUseCase,
    private val postInterestProtectingAnimalUseCase: PostInterestProtectingAnimalUseCase
) : ViewModel() {

    private val _detailSearchData = MutableStateFlow<DetailProtectData?>(null)
    val detailSearchData = _detailSearchData.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun getDetailSearchProtect(protectingReportId: Long) {
        viewModelScope.launch {
            getDetailSearchUseCase.getProtectData(protectingReportId).fold(
                onSuccess = { data ->
                    _detailSearchData.value = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "구조 동물 상세 정보를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun setInterestProtectingAnimal(id: Long) {
        if (_detailSearchData.value?.interest == true) {
            viewModelScope.launch {
                postInterestProtectingAnimalUseCase(id).fold(
                    onSuccess = { },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "신고 동물 관심 등록 중 오류가 발생했습니다."
                    }
                )
            }
        } else {

        }
    }

}