package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.DetailSearchData
import com.example.findu.domain.repository.DetailSearchRepository

class GetDetailSearchUseCase(
    private val detailSearchRepository: DetailSearchRepository
) {
    suspend fun getReportData(): Result<DetailSearchData> =
        detailSearchRepository.getDetailSearchReport()

    suspend fun getProtectData(): Result<DetailSearchData> =
        detailSearchRepository.getDetailSearchProtect()
}