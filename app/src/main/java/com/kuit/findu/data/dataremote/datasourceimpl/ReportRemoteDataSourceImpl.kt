package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.MissingReportRequestDto
import com.kuit.findu.data.dataremote.model.request.WitnessReportRequestDto
import com.kuit.findu.data.dataremote.model.response.report.ImageUploadResponseDto
import com.kuit.findu.data.dataremote.service.ReportService
import okhttp3.MultipartBody
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor(
    private val service: ReportService
) : ReportRemoteDataSource {
    override suspend fun uploadImages(files: List<MultipartBody.Part>): BaseResponse<ImageUploadResponseDto> =
        service.uploadImages(files)

    override suspend fun postMissingReport(requestDto: MissingReportRequestDto): NullableBaseResponse<Unit> =
        service.postMissingReport(requestDto)

    override suspend fun postWitnessReport(requestDto: WitnessReportRequestDto): NullableBaseResponse<Unit> =
        service.postWitnessReport(requestDto)

    override suspend fun deleteReport(reportId: Long): NullableBaseResponse<Unit> =
        service.deleteReport(reportId)

}