package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.DetailProtectResponseDto
import com.example.findu.data.dataremote.model.response.DetailReportResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailSearchService {
    @GET("/api/v1/reports/protecting-animals/{protecting_report_id}")
    suspend fun getDetailSearchProtect(
        @Path("protecting_report_id") protectingReportId: Long
        ): BaseResponse<DetailProtectResponseDto>

    @GET("/api/v1/reports/report-animals/{report_id}")
    suspend fun getDetailSearchReport(
        @Path("report_id") reportId: Long
    ): BaseResponse<DetailReportResponseDto>

}