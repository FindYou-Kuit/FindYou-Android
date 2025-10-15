package com.example.findu.data.repositoryimpl


import com.example.findu.data.dataremote.datasource.InquiryRemoteDataSource
import com.example.findu.data.dataremote.model.request.PostInquiryRequestDto
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.domain.repository.InquiryRepository
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