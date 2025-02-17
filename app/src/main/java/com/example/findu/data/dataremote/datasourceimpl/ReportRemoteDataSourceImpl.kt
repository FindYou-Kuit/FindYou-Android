package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.ReportRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.service.ReportService
import okhttp3.MultipartBody
import javax.inject.Inject

class ReportRemoteDataSourceImpl @Inject constructor(
    private val service: ReportService
) : ReportRemoteDataSource {
    override suspend fun uploadImages(files: List<MultipartBody.Part>): BaseResponse<List<Int>> =
        service.uploadImages(files)
}