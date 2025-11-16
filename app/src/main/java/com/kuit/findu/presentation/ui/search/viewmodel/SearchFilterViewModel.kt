package com.kuit.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.findu.domain.model.breed.SpeciesType
import com.kuit.findu.domain.model.extra.Sido
import com.kuit.findu.domain.repository.BreedRepository
import com.kuit.findu.domain.usecase.extra.GetSidoUseCase
import com.kuit.findu.domain.usecase.extra.GetSigunguUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val breedRepository: BreedRepository,
    private val getSidoUseCase: GetSidoUseCase,
    private val getSigunguUseCase: GetSigunguUseCase,
) : ViewModel() {

    private val _breedList = MutableStateFlow<List<String>>(emptyList())
    val breedList: StateFlow<List<String>> = _breedList

    private val _sidoList = MutableStateFlow<List<Sido>>(emptyList())
    val sidoList: StateFlow<List<Sido>> = _sidoList

    private val _sigunguList = MutableStateFlow<List<String>>(emptyList())
    val sigunguList: StateFlow<List<String>> = _sigunguList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadBreeds(species: SpeciesType) {
        viewModelScope.launch {
            val result = breedRepository.getBreedData()
            result
                .onSuccess { data ->
                    _breedList.value = when (species) {
                        SpeciesType.DOG -> data.dogBreedList.map { it.breedName }
                        SpeciesType.CAT -> data.catBreedList.map { it.breedName }
                        SpeciesType.ETC -> data.etcBreedList.map { it.breedName }
                    }
                }
                .onFailure { e ->
                    _errorMessage.value = e.message ?: "품종 정보를 불러오지 못했습니다."
                }
        }
    }

    fun loadSido() {
        viewModelScope.launch {
            getSidoUseCase().onSuccess {
                _sidoList.value = it
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }

    fun loadSigungu(sidoId: Long) {
        viewModelScope.launch {
            getSigunguUseCase(sidoId).onSuccess {
                _sigunguList.value = it
            }.onFailure {
                _errorMessage.value = it.message
            }
        }
    }
}