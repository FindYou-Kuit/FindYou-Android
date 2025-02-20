package com.example.findu.domain.usecase.report

import com.example.findu.domain.repository.report.ReportRepository
import javax.inject.Inject

class DeleteReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: Long): Result<Unit> =
        reportRepository.deleteReport(reportId)
}