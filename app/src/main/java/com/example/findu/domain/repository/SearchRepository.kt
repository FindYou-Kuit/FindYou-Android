package com.example.findu.domain.repository

import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.model.search.SearchFilterData

interface SearchRepository {
    suspend fun getSearchAll(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>

    suspend fun getSearchReport(
        searchFilterData: SearchFilterData?,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>

    suspend fun getSearchProtect(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>
}