package com.kuit.findu.domain.repository


interface InquiryRepository {
    suspend fun postInquiry(
        title: String,
        content: String,
        categories: List<String>
    ): Result<Unit>
}