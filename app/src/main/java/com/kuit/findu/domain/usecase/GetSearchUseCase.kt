package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.model.search.SearchData
import com.kuit.findu.domain.model.search.SearchFilterData
import com.kuit.findu.domain.repository.SearchRepository

class GetSearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend fun getReports(
        type: String,
        searchFilterData: SearchFilterData?,
        lastId: Long = Long.MAX_VALUE
    ): Result<List<SearchData>> =
        searchRepository.getReports(
            type = type,
            searchFilterData = searchFilterData,
            lastId = lastId
        )
}