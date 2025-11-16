package com.kuit.findu.data.repositoryimpl


import com.kuit.findu.data.dataremote.datasource.InquiryRemoteDataSource
import com.kuit.findu.data.dataremote.model.request.PostInquiryRequestDto
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.domain.repository.InquiryRepository
import javax.inject.Inject

class InquiryRepositoryImpl @Inject constructor(
    private val inquiryRemoteDataSource: InquiryRemoteDataSource,
) : InquiryRepository {

    override suspend fun postInquiry(
        title: String,
        content: String,
        categories: List<String>
    ): Result<Unit> = runCatching {
        inquiryRemoteDataSource
            .postInquiry(PostInquiryRequestDto(title, content, categories))
            .handleBaseResponse()
            .getOrThrow()
    }
}