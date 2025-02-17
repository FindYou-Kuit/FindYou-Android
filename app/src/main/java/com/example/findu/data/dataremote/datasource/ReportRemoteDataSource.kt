package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import okhttp3.MultipartBody

interface ReportRemoteDataSource {
    suspend fun uploadImages(files: List<MultipartBody.Part>): BaseResponse<List<Int>>
}