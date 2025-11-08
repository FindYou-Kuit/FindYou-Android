package com.kuit.findu.data.dataremote.service

import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.PostInquiryRequestDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface InquiryService {
    @POST("/api/v2/inquiries")
    suspend fun postInquiry(
        @Body body: PostInquiryRequestDto
    ): NullableBaseResponse<Unit>
}