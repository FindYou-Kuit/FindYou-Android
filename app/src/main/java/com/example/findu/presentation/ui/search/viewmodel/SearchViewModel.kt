package com.example.findu.presentation.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.usecase.GetBreedDataUseCase
import com.example.findu.domain.usecase.GetSearchUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.DeleteInterestReportAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestReportAnimalUseCase
import com.example.findu.presentation.mapper.todomain.toDomain
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.toSearchFilterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val postInterestProtectingAnimalUseCase: PostInterestProtectingAnimalUseCase,
    private val postInterestReportAnimalUseCase: PostInterestReportAnimalUseCase,
    private val deleteInterestProtectingAnimalUseCase: DeleteInterestProtectingAnimalUseCase,
    private val deleteInterestReportAnimalUseCase: DeleteInterestReportAnimalUseCase,
    private val getBreedDataUseCase: GetBreedDataUseCase
) : ViewModel() {

    private var _allFilter: SearchFilterUiModel? = SearchFilterUiModel()
    val allFilter: SearchFilterUiModel?
        get() = _allFilter

    private var _reportFilter: SearchFilterUiModel? = SearchFilterUiModel()
    val reportFilter: SearchFilterUiModel?
        get() = _reportFilter

    private var _protectFilter: SearchFilterUiModel? = SearchFilterUiModel()
    val protectFilter: SearchFilterUiModel?
        get() = _protectFilter

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

    fun getSearchAllData(
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ) {
        viewModelScope.launch {
            getSearchUseCase.getAllData(
                _allFilter?.toDomain(),
                lastProtectId = lastProtectId,
                lastReportId = lastReportId
            ).fold(
                onSuccess = { data ->
                    _allSearchData.value = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun getSearchReportData(
        lastReportId: Long = Long.MAX_VALUE
    ) {
        viewModelScope.launch {
            getSearchUseCase.getReportData(
                _reportFilter?.toDomain(),
                lastReportId = lastReportId
            ).fold(
                onSuccess = { data ->
                    _reportSearchData.value = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "신고 동물 데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun getSearchProtectData(
        lastProtectId: Long = Long.MAX_VALUE
    ) {
        viewModelScope.launch {
            getSearchUseCase.getProtectData(
                _protectFilter?.toDomain(),
                lastProtectId = lastProtectId,
            ).fold(
                onSuccess = { data ->
                    _protectSearchData.value = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "신고 동물 데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun updateAllFilterState(
        searchFilterUiModel: SearchFilterUiModel?
    ) {
        val newAllFilter = searchFilterUiModel?.toSearchFilterUiModel() ?: SearchFilterUiModel()
        if (newAllFilter != _allFilter) {
            _allFilter = newAllFilter
            Log.d("SearchViewModel", "updateAllFilterState: $newAllFilter")
            getSearchAllData()
        }
    }

    fun updateReportFilterState(
        searchFilterUiModel: SearchFilterUiModel?
    ) {
        val newReportFilter = searchFilterUiModel?.toSearchFilterUiModel() ?: SearchFilterUiModel()
        if (newReportFilter != _reportFilter) {
            _reportFilter = newReportFilter
            Log.d("SearchViewModel", "updateReportFilterState: $newReportFilter")
            getSearchReportData()
        }
    }

    fun updateProtectFilterState(
        searchFilterUiModel: SearchFilterUiModel?
    ) {
        val newProtectFilter = searchFilterUiModel?.toSearchFilterUiModel() ?: SearchFilterUiModel()
        if (newProtectFilter != _protectFilter) {
            _protectFilter = newProtectFilter
            Log.d("SearchViewModel", "updateProtectFilterState: $newProtectFilter")
            getSearchProtectData()
        }
    }

    fun setInterest(
        id: Long,
        isInterest: Boolean,
        tag: String
    ) {
        when (tag) {
            "보호중" -> postProtectInterest(id, isInterest)
            "목격신고" -> postReportInterest(id, isInterest)
            "실종신고" -> postReportInterest(id, isInterest)
            else -> {
                _errorMessage.value = "잘못된 태그 값입니다."
            }
        }

    }

    private fun postProtectInterest(id: Long, isInterest: Boolean) {
        viewModelScope.launch {
            if (isInterest) {
                postInterestProtectingAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 등록 중 오류가 발생했습니다."
                    }
                )
            } else {
                deleteInterestProtectingAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 해제 중 오류가 발생했습니다."
                    }
                )
            }
        }
    }

    private fun postReportInterest(id: Long, isInterest: Boolean) {
        viewModelScope.launch {
            if (isInterest) {
                postInterestReportAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 등록 중 오류가 발생했습니다."
                    }
                )
            } else {
                deleteInterestReportAnimalUseCase(id).fold(
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

    fun selectSpeciesType(speciesType: SpeciesType) {
        _selectedSpeciesType.value = speciesType
        when (speciesType) {
            SpeciesType.DOG -> _selectedBreedList.value =
                _breedData.value?.dogBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.CAT -> _selectedBreedList.value =
                _breedData.value?.catBreedList?.map { it.breedName } ?: emptyList()

            SpeciesType.ETC -> _selectedBreedList.value =
                _breedData.value?.etcBreedList?.map { it.breedName } ?: emptyList()
        }
    }

}