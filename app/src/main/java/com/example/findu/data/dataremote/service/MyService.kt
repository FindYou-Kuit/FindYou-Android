package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {
    @GET("/api/v1/users/interest-animals")
    suspend fun getInterestAnimals(
        @Query("lastInterestReportId") lastReportId: Long,
        @Query("lastInterestProtectId") lastProtectId: Long,
    ): BaseResponse<MyInterestResponseDto>

    @GET("/api/v1/users/reports")
    suspend fun getReportHistory(
        @Query("lastReportId") lastReportId: Long,
    ): BaseResponse<MyReportHistoryResponseDto>
}