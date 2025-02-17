package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.DetailProtectResponseDto
import com.example.findu.data.dataremote.model.response.DetailReportResponseDto

interface DetailSearchRemoteDataSource {
    suspend fun getDetailSearchProtect(protectingReportId: Long): BaseResponse<DetailProtectResponseDto>
    suspend fun getDetailSearchReport(reportId: Long): BaseResponse<DetailReportResponseDto>

}