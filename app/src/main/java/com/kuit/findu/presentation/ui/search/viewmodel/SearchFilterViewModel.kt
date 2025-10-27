package com.kuit.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.findu.domain.model.breed.SpeciesType
import com.kuit.findu.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val breedRepository: BreedRepository
) : ViewModel() {

    private val _breedList = MutableStateFlow<List<String>>(emptyList())
    val breedList: StateFlow<List<String>> = _breedList

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
}