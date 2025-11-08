package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.PostInquiryRequestDto

interface InquiryRemoteDataSource {
    suspend fun postInquiry( request: PostInquiryRequestDto): NullableBaseResponse<Unit>
}