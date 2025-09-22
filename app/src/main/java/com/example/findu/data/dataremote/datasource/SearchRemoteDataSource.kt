package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import com.example.findu.domain.model.search.SearchFilterData
import retrofit2.http.Query

interface SearchRemoteDataSource {
    suspend fun getReports(
        type: String,
        searchFilterData: SearchFilterData?,
        lastId: Long = Long.MAX_VALUE,
    ): BaseResponse<SearchResponseDto>
}