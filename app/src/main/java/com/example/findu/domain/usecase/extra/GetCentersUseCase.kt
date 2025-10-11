package com.example.findu.domain.usecase.extra

import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.PagedResult
import com.example.findu.domain.repository.InformationRepository

class GetCentersUseCase (
    private val repository: InformationRepository
) {
    suspend operator fun invoke(
        lastId: Long? = Long.MAX_VALUE,
        sido: String? = null,
        sigungu: String? = null,
        lat: Double? = null,
        lon: Double? = null
    ) :Result<PagedResult<Center>> = repository.getCenters(
        lastId = lastId,
        sido = sido,
        sigungu = sigungu,
        lat = lat,
        long = lon
    )
}