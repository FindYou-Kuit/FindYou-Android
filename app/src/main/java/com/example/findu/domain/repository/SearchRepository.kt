package com.example.findu.domain.repository

import com.example.findu.domain.model.search.SearchData

interface SearchRepository {
    suspend fun getSearchAll(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>

    suspend fun getSearchReport(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>

    suspend fun getSearchProtect(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>
}