package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.repository.SearchRepository

class GetSearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun getAllData(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchAll(
            startDate = startDate,
            endDate = endDate,
            species = species,
            breeds = breeds,
            location = location,
            lastProtectId = lastProtectId,
            lastReportId = lastReportId
        )

    suspend fun getProtectData(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchProtect(
            startDate = startDate,
            endDate = endDate,
            species = species,
            breeds = breeds,
            location = location,
            lastProtectId = lastProtectId,
        )

    suspend fun getReportData(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getSearchReport(
            startDate = startDate,
            endDate = endDate,
            species = species,
            breeds = breeds,
            location = location,
            lastReportId = lastReportId
        )
}