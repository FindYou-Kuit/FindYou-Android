package com.example.findu.domain.usecase.report

import com.example.findu.domain.repository.report.ReportRepository
import okhttp3.MultipartBody

class UploadImagesUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(files: List<MultipartBody.Part>): Result<List<Int>> =
        reportRepository.uploadImages(files)
}