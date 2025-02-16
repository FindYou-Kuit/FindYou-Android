package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.DetailSearchData
import com.example.findu.domain.repository.DetailSearchRepository

class GetDetailSearchUseCase(
    private val detailSearchRepository: DetailSearchRepository
) {
    suspend operator fun invoke(): Result<DetailSearchData> =
        detailSearchRepository.getDetailSearchReport()
}