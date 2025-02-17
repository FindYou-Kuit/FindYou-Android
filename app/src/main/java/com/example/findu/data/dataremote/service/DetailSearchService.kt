package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.DetailSearchResponseDto
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailSearchService {
    @GET("/api/v1/reports/protecting-animals/{protecting_report_id}")
    suspend fun getDetailSearchProtect(
        @Path("protecting_report_id") protectingReportId: Long
        ): BaseResponse<DetailSearchResponseDto>

    @GET("/api/v1/users/interest-animals/report-animals/{report_animal_id}")
    suspend fun getDetailSearchReport(
        @Path("report_id") reportId: Long
    ): BaseResponse<DetailSearchResponseDto>

}