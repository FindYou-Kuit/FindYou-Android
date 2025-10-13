package com.example.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.GetSearchUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestAnimalUseCase
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.SearchType
import com.example.findu.presentation.ui.search.model.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val postInterestAnimalUseCase: PostInterestAnimalUseCase,
    private val deleteInterestAnimalUseCase: DeleteInterestAnimalUseCase,
    private val getBreedDataUseCase: GetBreedDataUseCase
) : ViewModel() {

    private var _allFilter: SearchFilterUiModel? = null
    private var _reportFilter: SearchFilterUiModel? = null
    private var _protectFilter: SearchFilterUiModel? = null

    private val _allSearchData = MutableStateFlow<List<SearchData>?>(null)
    val allSearchData = _allSearchData.asStateFlow()

    private val _reportSearchData = MutableStateFlow<List<SearchData>?>(null)
    val reportSearchData = _reportSearchData.asStateFlow()

    private val _protectSearchData = MutableStateFlow<List<SearchData>?>(null)
    val protectSearchData = _protectSearchData.asStateFlow()

    private val _breedData = MutableStateFlow<BreedData?>(null)
    val breedData: StateFlow<BreedData?> = _breedData

    private val _selectedBreedList = MutableStateFlow<List<String>>(emptyList())
    val selectedBreedList: StateFlow<List<String>> = _selectedBreedList

    private val _selectedSpeciesType = MutableStateFlow<SpeciesType?>(null)

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        fetchBreedData()
    }

    fun getSearchData(
        type: SearchType,
        lastId: Long = Long.MAX_VALUE,
    ) {
        viewModelScope.launch {
            val filter = when (type) {
                SearchType.ALL -> _allFilter?.toDomain()
                SearchType.PROTECTING -> _protectFilter?.toDomain()
                SearchType.REPORTING -> _reportFilter?.toDomain()
            }

            getSearchUseCase.getReports(
                type = type.name,
                searchFilterData = filter,
                lastId = lastId
            ).fold(
                onSuccess = { data ->
                    when (type) {
                        SearchType.ALL -> _allSearchData.value = data
                        SearchType.PROTECTING -> _protectSearchData.value = data
                        SearchType.REPORTING -> _reportSearchData.value = data
                    }
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun updateAllFilterState(filterUiModel: SearchFilterUiModel?) {
        _allFilter = filterUiModel?.copy()
    }

    fun updateReportFilterState(filterUiModel: SearchFilterUiModel?) {
        _reportFilter = filterUiModel?.copy()
    }

    fun updateProtectFilterState(filterUiModel: SearchFilterUiModel?) {
        _protectFilter = filterUiModel?.copy()
    }

    fun clearFilterState(type: SearchType) {
        when (type) {
            SearchType.ALL -> _allFilter = null
            SearchType.PROTECTING -> _protectFilter = null
            SearchType.REPORTING -> _reportFilter = null
        }
    }

    fun setInterest(
        id: Long,
        isInterest: Boolean,
        tag: String,
    ) {
        viewModelScope.launch {
            if (isInterest) {
                postInterestAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 등록 중 오류가 발생했습니다."
                    }
                )
            } else {
                deleteInterestAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 해제 중 오류가 발생했습니다."
                    }
                )
            }
        }
    }

    private fun fetchBreedData() {
        viewModelScope.launch {
            getBreedDataUseCase().fold(
                onSuccess = { data ->
                    _breedData.value = data
                    _selectedBreedList.value = data.etcBreedList.map { it.breedName }
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "품종 데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }
}