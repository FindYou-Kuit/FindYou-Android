package com.example.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.search.DetailMissingData
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailWitnessData
import com.example.findu.domain.usecase.GetDetailSearchUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestReportAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestReportAnimalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailSearchViewModel @Inject constructor(
    private val getDetailSearchUseCase: GetDetailSearchUseCase,
    private val postInterestReportAnimalUseCase: PostInterestReportAnimalUseCase,
    private val deleteInterestReportAnimalUseCase: DeleteInterestReportAnimalUseCase,
    private val postInterestProtectingAnimalUseCase: PostInterestProtectingAnimalUseCase,
    private val deleteInterestProtectingAnimalUseCase: DeleteInterestProtectingAnimalUseCase,
) : ViewModel() {

    private val _detailMissingData = MutableStateFlow<DetailMissingData?>(null)
    val detailMissingData = _detailMissingData.asStateFlow()

    private val _detailWitnessData = MutableStateFlow<DetailWitnessData?>(null)
    val detailWitnessData = _detailWitnessData.asStateFlow()

    private val _detailProtectData = MutableStateFlow<DetailProtectData?>(null)
    val detailProtectData = _detailProtectData.asStateFlow()

    private val _isInterested = MutableStateFlow(false)
    val isInterested = _isInterested.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun getDetailSearchMissing(id: Long) {
        viewModelScope.launch {
            getDetailSearchUseCase.getMissingData(id).fold(
                onSuccess = {
                    _detailMissingData.value = it
                    _isInterested.value = it.interest
                },
                onFailure = { error -> _errorMessage.value = error.message ?: "실종 상세 조회 실패" }
            )
        }
    }

    fun getDetailSearchWitness(id: Long) {
        viewModelScope.launch {
            getDetailSearchUseCase.getWitnessData(id).fold(
                onSuccess = {
                    _detailWitnessData.value = it
                    _isInterested.value = it.interest
                },
                onFailure = { error -> _errorMessage.value = error.message ?: "목격 상세 조회 실패" }
            )
        }
    }

    fun getDetailSearchProtect(id: Long) {
        viewModelScope.launch {
            getDetailSearchUseCase.getProtectData(id).fold(
                onSuccess = {
                    _detailProtectData.value = it
                    _isInterested.value = it.interest
                },
                onFailure = { error -> _errorMessage.value = error.message ?: "보호 상세 조회 실패" }
            )
        }
    }

    fun toggleInterestMissing(id: Long) {
        viewModelScope.launch {
            val current = _detailMissingData.value?.interest ?: false
            if (!current) {
                postInterestReportAnimalUseCase(id).fold(
                    onSuccess = {
                        _detailMissingData.value = _detailMissingData.value?.copy(interest = true)
                        _isInterested.value = true

                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "실종 관심 등록 실패"
                    }
                )
            } else {
                deleteInterestReportAnimalUseCase(id).fold(
                    onSuccess = {
                        _detailMissingData.value = _detailMissingData.value?.copy(interest = false)
                        _isInterested.value = false

                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "실종 관심 해제 실패"
                    }
                )
            }
        }
    }

    fun toggleInterestWitness(id: Long) {
        viewModelScope.launch {
            val current = _detailWitnessData.value?.interest ?: false
            if (!current) {
                postInterestReportAnimalUseCase(id).fold(
                    onSuccess = {
                        _detailWitnessData.value = _detailWitnessData.value?.copy(interest = true)
                        _isInterested.value = true
                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "목격 관심 등록 실패"
                    }
                )
            } else {
                deleteInterestReportAnimalUseCase(id).fold(
                    onSuccess = {
                        _detailWitnessData.value = _detailWitnessData.value?.copy(interest = false)
                        _isInterested.value = false
                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "목격 관심 해제 실패"
                    }
                )
            }
        }
    }

    fun toggleInterestProtect(id: Long) {
        viewModelScope.launch {
            val current = _detailProtectData.value?.interest ?: false
            if (!current) {
                postInterestProtectingAnimalUseCase(id).fold(
                    onSuccess = {
                        _detailProtectData.value = _detailProtectData.value?.copy(interest = true)
                        _isInterested.value = true
                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "보호 관심 등록 실패"
                        _isInterested.value = false
                    }
                )
            } else {
                deleteInterestProtectingAnimalUseCase(id).fold(
                    onSuccess = {
                        _detailProtectData.value = _detailProtectData.value?.copy(interest = false)
                    },
                    onFailure = { error ->
                        _errorMessage.value = error.message ?: "보호 관심 해제 실패"
                    }
                )
            }
        }
    }
}