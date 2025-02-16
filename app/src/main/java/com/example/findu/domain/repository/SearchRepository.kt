package com.example.findu.domain.repository

import com.example.findu.domain.model.search.SearchData

interface SearchRepository {
    suspend fun getSearchAll(
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>

    suspend fun getSearchReport(
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>

    suspend fun getSearchProtect(
        lastProtectId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>
}