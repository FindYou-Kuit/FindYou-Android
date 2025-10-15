package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.InquiryRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.PostInquiryRequestDto
import com.example.findu.data.dataremote.service.InquiryService
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.domain.repository.InquiryRepository
import javax.inject.Inject


class InquiryRemoteDataSourceImpl @Inject constructor(
    private val inquiryService: InquiryService
) : InquiryRemoteDataSource {
    override suspend fun postInquiry(request: PostInquiryRequestDto)
            = inquiryService.postInquiry(request)
}