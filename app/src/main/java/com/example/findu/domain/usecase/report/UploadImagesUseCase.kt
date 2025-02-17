package com.example.findu.domain.usecase.report

import com.example.findu.domain.repository.report.ReportRepository

class UploadImagesUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(images: List<String>): Result<List<Int>> =
        reportRepository.uploadImages(images)
}