package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.data.dataremote.model.request.WitnessReportRequestDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ReportService {
    @Multipart
    @POST("/api/v1/reports/images")
    suspend fun uploadImages(
        @Part("files") files: List<MultipartBody.Part?>
    ): BaseResponse<List<String>>

    @POST("/api/v1/reports/new-missing-reports")
    suspend fun postMissingReport(
        @Body request: MissingReportRequestDto
    ): NullableBaseResponse<Unit>

    @POST("/api/v1/reports/new-witness-reports")
    suspend fun postWitnessReport(
        @Body request: WitnessReportRequestDto
    ): NullableBaseResponse<Unit>

}