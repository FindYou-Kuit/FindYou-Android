package com.example.findu.presentation.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.usecase.GetSearchUseCase
import com.example.findu.presentation.mapper.todomain.toDomain
import com.example.findu.presentation.model.SearchFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase
) : ViewModel() {

    private var _allFilter: SearchFilters? = SearchFilters()

    private var _reportFilter: SearchFilters? = SearchFilters()

    private var _protectFilter: SearchFilters? = SearchFilters()

    private val _allSearchData = MutableStateFlow<List<SearchData>?>(null)
    val allSearchData = _allSearchData.asStateFlow()

    private val _reportSearchData = MutableStateFlow<List<SearchData>?>(null)
    val reportSearchData = _reportSearchData.asStateFlow()

    private val _protectSearchData = MutableStateFlow<List<SearchData>?>(null)
    val protectSearchData = _protectSearchData.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

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
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: List<String>?,
        location: String?,
    ) {
        val speciesType = when (species) {
            "개" -> "강아지"
            "고양이" -> "고양이"
            "기타" -> "기타"
            else -> null
        }

        val newAllFilter = SearchFilters(
            startDate = startDate ?: _allFilter?.startDate,
            endDate = endDate ?: _allFilter?.endDate,
            species = speciesType ?: _allFilter?.species,
            breeds = breeds ?: _allFilter?.breeds,
            location = location ?: _allFilter?.location
        )
        if (newAllFilter != _allFilter) {
            _allFilter = newAllFilter
            getSearchAllData()
        }
    }

    fun updateReportFilterState(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: List<String>?,
        location: String?,
    ) {
        val speciesType = when (species) {
            "개" -> "강아지"
            "고양이" -> "고양이"
            "기타" -> "기타"
            else -> null
        }

        val newReportFilter = SearchFilters(
            startDate = startDate ?: _reportFilter?.startDate,
            endDate = endDate ?: _reportFilter?.endDate,
            species = speciesType ?: _reportFilter?.species,
            breeds = breeds ?: _reportFilter?.breeds,
            location = location ?: _reportFilter?.location
        )
        if (newReportFilter != _reportFilter) {
            _reportFilter = newReportFilter
            getSearchReportData()
        }
    }

    fun updateProtectFilterState(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: List<String>?,
        location: String?,
    ) {
        val speciesType = when (species) {
            "개" -> "강아지"
            "고양이" -> "고양이"
            "기타" -> "기타"
            else -> null
        }
        val newProtectFilter = SearchFilters(
            startDate = startDate ?: _protectFilter?.startDate,
            endDate = endDate ?: _protectFilter?.endDate,
            species = speciesType ?: _protectFilter?.species,
            breeds = breeds ?: _protectFilter?.breeds,
            location = location ?: _protectFilter?.location
        )

        if (newProtectFilter != _protectFilter) {
            _protectFilter = newProtectFilter
            getSearchProtectData()
        }
    }
}