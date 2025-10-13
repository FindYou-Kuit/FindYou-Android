package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.DetailMissingData
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailWitnessData
import com.example.findu.domain.repository.DetailSearchRepository

class GetDetailSearchUseCase(
    private val detailSearchRepository: DetailSearchRepository
) {
    suspend fun getMissingData(reportId: Long): Result<DetailMissingData> =
        detailSearchRepository.getDetailSearchMissing(reportId)

    suspend fun getWitnessData(reportId: Long): Result<DetailWitnessData> =
        detailSearchRepository.getDetailSearchWitness(reportId)

    suspend fun getProtectData(protectingReportId: Long): Result<DetailProtectData> =
        detailSearchRepository.getDetailSearchProtect(protectingReportId)
}