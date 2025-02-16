package com.example.findu.domain.repository

import com.example.findu.domain.model.search.SearchData

interface SearchRepository {
    suspend fun getSearch(
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>
}