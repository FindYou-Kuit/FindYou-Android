package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchService {
    @GET("/api/v1/reports")
    suspend fun getSearch(
        @Header("Authorization") token: String,
        @Query("lastProtectId") lastProtectId: Long,
        @Query("lastReportId") lastReportId: Long
    ): BaseResponse<SearchResponseDto>
}