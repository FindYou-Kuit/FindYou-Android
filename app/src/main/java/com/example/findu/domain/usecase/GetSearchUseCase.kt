package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.repository.SearchRepository

class GetSearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun getAllData(
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchAll(lastProtectId, lastReportId)

    suspend fun getProtectData(
        lastProtectId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchProtect(lastProtectId)

    suspend fun getReportData(
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchReport(lastReportId)
}