package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.DetailSearchData
import com.example.findu.domain.repository.DetailSearchRepository

class GetDetailSearchUseCase(
    private val detailSearchRepository: DetailSearchRepository
) {
    suspend fun getReportData(reportId: Long): Result<DetailSearchData> =
        detailSearchRepository.getDetailSearchReport(reportId)

    suspend fun getProtectData(protectingReportId: Long): Result<DetailSearchData> =
        detailSearchRepository.getDetailSearchProtect(protectingReportId)
}