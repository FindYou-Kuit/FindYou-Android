package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface InterestService {
    @POST("/api/v1/users/interest-animals/protecting-animals")
    suspend fun getInterestProtectingAnimals(
        @Body id : Long
    ) : NullableBaseResponse<Int>

    @POST("/api/v1/users/interest-animals/report-animals")
    suspend fun getInterestReportAnimals(
        @Body id : Long
    ) : NullableBaseResponse<Int>

    @DELETE("/api/v1/users/interest-animals/protecting-animals/{protecting_report_id}")
    suspend fun deleteInterestProtectingAnimals(
        @Path("protecting_report_id") reportId : Long
    ) : NullableBaseResponse<Unit>

    @DELETE("/api/v1/users/interest-animals/report-animals/{report_id}")
    suspend fun deleteInterestReportAnimals(
        @Path("report_id") reportId : Long
    ) : NullableBaseResponse<Unit>
}