package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.report.ReportImageResponseDto

interface ReportRemoteDataSource {
    suspend fun uploadImages(images: List<String>): BaseResponse<ReportImageResponseDto>
}