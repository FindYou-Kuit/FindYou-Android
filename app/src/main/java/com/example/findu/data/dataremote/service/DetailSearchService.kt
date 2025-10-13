package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.search.DetailMissingResponseDto
import com.example.findu.data.dataremote.model.response.search.DetailProtectResponseDto
import com.example.findu.data.dataremote.model.response.search.DetailWitnessResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailSearchService {
        @GET("/api/v2/reports/protecting-reports/{report_id}")
        suspend fun getDetailSearchProtect(
            @Path("report_id") reportId: Long
        ): BaseResponse<DetailProtectResponseDto>

        @GET("/api/v2/reports/missing-reports/{report_id}")
        suspend fun getDetailSearchMissing(
            @Path("report_id") reportId: Long
        ): BaseResponse<DetailMissingResponseDto>

        @GET("/api/v2/reports/witness-reports/{report_id}")
        suspend fun getDetailSearchWitness(
            @Path("report_id") reportId: Long
        ): BaseResponse<DetailWitnessResponseDto>

}