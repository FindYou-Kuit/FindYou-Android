package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {
    @GET("/api/v1/users/interest-animals")
    suspend fun getInterestAnimals(
        @Query("lastInterestReportId") lastReportId: Long = Long.MAX_VALUE,
        @Query("lastInterestProtectId") lastProtectId: Long = Long.MAX_VALUE,
    ): BaseResponse<MyInterestResponseDto>
}