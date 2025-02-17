package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.DetailProtectResponseDto
import com.example.findu.data.dataremote.model.response.DetailReportResponseDto
import com.example.findu.data.dataremote.service.DetailSearchService
import javax.inject.Inject

class DetailSearchRemoteDataSourceImpl @Inject constructor(
    private val detailService: DetailSearchService
): DetailSearchRemoteDataSource {
    override suspend fun getDetailSearchReport(reportId: Long): BaseResponse<DetailReportResponseDto> =
        detailService.getDetailSearchReport(reportId)
    override suspend fun getDetailSearchProtect(protectingReportId: Long): BaseResponse<DetailProtectResponseDto> =
        detailService.getDetailSearchProtect(protectingReportId)
}