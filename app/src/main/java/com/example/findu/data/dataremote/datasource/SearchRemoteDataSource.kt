package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import retrofit2.http.Query

interface SearchRemoteDataSource {
    suspend fun getSearchAll(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long = Long.MAX_VALUE,
        lastReportId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

    suspend fun getSearchReport(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?, 
        lastReportId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

    suspend fun getSearchProtect(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>
}