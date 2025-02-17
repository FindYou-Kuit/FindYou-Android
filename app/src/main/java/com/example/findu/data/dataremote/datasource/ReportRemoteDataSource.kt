package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import okhttp3.MultipartBody

interface ReportRemoteDataSource {
    suspend fun uploadImages(files: List<MultipartBody.Part>): BaseResponse<List<Int>>

    suspend fun postMissingReport(requestDto: MissingReportRequestDto) : NullableBaseResponse<Unit>
}