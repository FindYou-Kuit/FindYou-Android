package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import retrofit2.http.Query

interface SearchRemoteDataSource {
    suspend fun getSearchAll(
        @Query("lastProtectId") lastProtectId: Long = Long.MAX_VALUE,
        @Query("lastReportId") lastReportId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

    suspend fun getSearchReport(
        @Query("lastReportId") lastReportId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

    suspend fun getSearchProtect(
        @Query("lastProtectId") lastProtectId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>
}