package com.example.findu.domain.usecase

import com.example.findu.domain.repository.InquiryRepository
import javax.inject.Inject

class PostInquiryUseCase @Inject constructor(
    private val inquiryRepository: InquiryRepository
) {
    suspend operator fun invoke(
        title: String,
        content: String,
        categories: List<String>
    ): Result<Unit> = inquiryRepository.postInquiry(title, content, categories)
}