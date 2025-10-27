package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.MissingReportRequestDto
import com.kuit.findu.data.dataremote.model.request.WitnessReportRequestDto
import com.kuit.findu.data.dataremote.model.response.report.ImageUploadResponseDto
import okhttp3.MultipartBody

interface ReportRemoteDataSource {
    suspend fun uploadImages(files: List<MultipartBody.Part>): BaseResponse<ImageUploadResponseDto>

    suspend fun postMissingReport(requestDto: MissingReportRequestDto) : NullableBaseResponse<Unit>

    suspend fun postWitnessReport(requestDto: WitnessReportRequestDto) : NullableBaseResponse<Unit>

    suspend fun deleteReport(reportId: Long) : NullableBaseResponse<Unit>
}