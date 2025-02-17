package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ReportService {
    @Multipart
    @POST("/api/v1/reports/images")
    suspend fun uploadImages(
        @Part("files") files: List<MultipartBody.Part?>
    ): BaseResponse<List<Int>>
}