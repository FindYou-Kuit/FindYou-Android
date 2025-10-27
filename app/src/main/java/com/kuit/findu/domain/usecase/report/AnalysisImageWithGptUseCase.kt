package com.kuit.findu.domain.usecase.report

import com.kuit.findu.domain.model.report.GptData
import com.kuit.findu.domain.repository.report.ReportRepository

class AnalysisImageWithGptUseCase(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(
        dogList : List<String>,
        catList : List<String>,
        etcList : List<String>,
        encodeString: String
    ): Result<GptData> =
        reportRepository.postImageAnalysis(
            dogList,
            catList,
            etcList,
            encodeString
        )
}