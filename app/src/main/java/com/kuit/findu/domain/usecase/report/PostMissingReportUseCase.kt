package com.kuit.findu.domain.usecase.report

import com.kuit.findu.domain.model.report.MissingReportData
import com.kuit.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class PostMissingReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(missingReportData: MissingReportData): Result<Unit> =
        reportRepository.postMissingReport(missingReportData)
}