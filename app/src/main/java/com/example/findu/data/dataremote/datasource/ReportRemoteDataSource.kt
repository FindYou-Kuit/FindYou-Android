package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.data.dataremote.model.request.WitnessReportRequestDto
import okhttp3.MultipartBody

interface ReportRemoteDataSource {
    suspend fun uploadImages(files: List<MultipartBody.Part>): BaseResponse<List<String>>

    suspend fun postMissingReport(requestDto: MissingReportRequestDto) : NullableBaseResponse<Unit>

    suspend fun postWitnessReport(requestDto: WitnessReportRequestDto) : NullableBaseResponse<Unit>

    suspend fun deleteReport(reportId: Long) : NullableBaseResponse<Unit>
}