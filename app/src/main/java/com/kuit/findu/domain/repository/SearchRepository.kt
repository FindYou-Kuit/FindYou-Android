package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.search.SearchData
import com.kuit.findu.domain.model.search.SearchFilterData

interface SearchRepository {
    suspend fun getReports(
        type: String,
        searchFilterData: SearchFilterData?,
        lastId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>>
}