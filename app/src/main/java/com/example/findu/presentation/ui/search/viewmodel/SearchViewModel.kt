package com.example.findu.presentation.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.usecase.GetSearchUseCase
import com.example.findu.presentation.mapper.todomain.toDomain
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.toSearchFilterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase
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
    fun setAllFilter(searchFilter: SearchFilterUiModel) {
        _allFilter = searchFilter
    }

    fun setReportFilter(searchFilter: SearchFilterUiModel) {
        _reportFilter = searchFilter
    }
    
    fun setProtectFilter(searchFilter: SearchFilterUiModel) {
        _protectFilter = searchFilter
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
        SearchFilterUiModel: SearchFilterUiModel?
    ) {
        val newAllFilter = SearchFilterUiModel?.toSearchFilterUiModel() ?: SearchFilterUiModel()
        if (newAllFilter != _allFilter) {
            _allFilter = newAllFilter
            Log.d("SearchViewModel", "updateAllFilterState: $newAllFilter")
            getSearchAllData()
        }
    }

    fun updateReportFilterState(
        SearchFilterUiModel: SearchFilterUiModel?
    ) {
        val newReportFilter = SearchFilterUiModel?.toSearchFilterUiModel() ?: SearchFilterUiModel()
        if (newReportFilter != _reportFilter) {
            _reportFilter = newReportFilter
            Log.d("SearchViewModel", "updateReportFilterState: $newReportFilter")
            getSearchReportData()
        }
    }

    fun updateProtectFilterState(
        SearchFilterUiModel: SearchFilterUiModel?
    ) {
        val newProtectFilter = SearchFilterUiModel?.toSearchFilterUiModel() ?: SearchFilterUiModel()
        if (newProtectFilter != _protectFilter) {
            _protectFilter = newProtectFilter
            Log.d("SearchViewModel", "updateProtectFilterState: $newProtectFilter")
            getSearchProtectData()
        }
    }
}