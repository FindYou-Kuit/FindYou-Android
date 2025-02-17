package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.report.ReportImageResponseDto
import okhttp3.MultipartBody

interface ReportRemoteDataSource {
    suspend fun uploadImages(images: List<MultipartBody.Part>): BaseResponse<ReportImageResponseDto>
}