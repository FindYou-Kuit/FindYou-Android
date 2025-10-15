package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.PostInquiryRequestDto

interface InquiryRemoteDataSource {
    suspend fun postInquiry( request: PostInquiryRequestDto): NullableBaseResponse<Unit>
}