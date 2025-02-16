package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/api/v1/reports")
    suspend fun getSearchAll(
        @Query("lastProtectId") lastProtectId: Long = Long.MAX_VALUE,
        @Query("lastReportId") lastReportId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

    @GET("/api/v1/reports/report-animals")
    suspend fun getSearchReport(
        @Query("lastReportId") lastReportId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

    @GET("/api/v1/reports/protecting-animals")
    suspend fun getSearchProtect(
        @Query("lastProtectId") lastProtectId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

}