package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.response.search.SearchResponseDto
import com.kuit.findu.domain.model.search.SearchFilterData

interface SearchRemoteDataSource {
    suspend fun getReports(
        type: String,
        searchFilterData: SearchFilterData?,
        lastId: Long = Long.MAX_VALUE,
    ): BaseResponse<SearchResponseDto>
}