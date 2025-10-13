package com.example.findu.domain.repository

import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.model.search.SearchFilterData

interface SearchRepository {
    suspend fun getReports(
        type: String,
        searchFilterData: SearchFilterData?,
        lastId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>
}