package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.data.dataremote.service.ReportService
import okhttp3.MultipartBody
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor(
    private val service: ReportService
) : ReportRemoteDataSource {
    override suspend fun uploadImages(files: List<MultipartBody.Part>): BaseResponse<List<String>> =
        service.uploadImages(files)

    override suspend fun postMissingReport(requestDto: MissingReportRequestDto): NullableBaseResponse<Unit> =
        service.postMissingReport(requestDto)

}