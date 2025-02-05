package com.example.findu.domain.usecase.report

import android.net.Uri
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.repository.report.ReportRepository

class AnalysisImageWithGptUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(encodeString: String): Result<GptData> =
        reportRepository.postImageAnalysis(encodeString)
}