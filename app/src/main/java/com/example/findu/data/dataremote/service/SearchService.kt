package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/api/v2/reports")
    suspend fun getReports(
        @Query("type") type: String,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("species") species: String? = null,
        @Query("breeds") breeds: String? = null,
        @Query("address") address: String? = null,
        @Query("lastId") lastId: Long = Long.MAX_VALUE
    ): BaseResponse<SearchResponseDto>

}