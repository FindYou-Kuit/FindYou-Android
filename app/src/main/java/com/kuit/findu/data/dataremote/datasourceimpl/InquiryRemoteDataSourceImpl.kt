package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.InquiryRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.PostInquiryRequestDto
import com.kuit.findu.data.dataremote.service.InquiryService
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.domain.repository.InquiryRepository
import javax.inject.Inject


class InquiryRemoteDataSourceImpl @Inject constructor(
    private val inquiryService: InquiryService
) : InquiryRemoteDataSource {
    override suspend fun postInquiry(request: PostInquiryRequestDto)
            = inquiryService.postInquiry(request)
}