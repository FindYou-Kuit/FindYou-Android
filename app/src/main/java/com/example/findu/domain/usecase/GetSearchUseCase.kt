package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.model.search.SearchFilterData
import com.example.findu.domain.repository.SearchRepository

class GetSearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun getAllData(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchAll(
            searchFilterData = searchFilterData,
            lastProtectId = lastProtectId,
            lastReportId = lastReportId
        )

    suspend fun getProtectData(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchProtect(
            searchFilterData = searchFilterData,
            lastProtectId = lastProtectId,
        )

    suspend fun getReportData(
        searchFilterData: SearchFilterData?,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchReport(
            searchFilterData = searchFilterData,
            lastReportId = lastReportId
        )
}