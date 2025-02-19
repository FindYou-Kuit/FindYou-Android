package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface InterestService {
    @POST("/api/v1/users/interest-animals/protecting-animals")
    suspend fun getInterestProtectingAnimals(
        @Body id : Long
    ) : NullableBaseResponse<Unit>

    @POST("/api/v1/users/interest-animals/report-animals")
    suspend fun getInterestReportAnimals(
        @Body id : Long
    ) : NullableBaseResponse<Unit>
}