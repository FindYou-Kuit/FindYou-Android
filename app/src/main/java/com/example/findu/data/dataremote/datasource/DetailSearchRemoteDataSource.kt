package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.DetailSearchResponseDto

interface DetailSearchRemoteDataSource {
    suspend fun getDetailSearchProtect(protectingReportId: Long): BaseResponse<DetailSearchResponseDto>
    suspend fun getDetailSearchReport(reportId: Long): BaseResponse<DetailSearchResponseDto>

}