package com.example.findu.domain.usecase.extra

import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.PagedResult
import com.example.findu.domain.repository.InformationRepository

class GetCentersUseCase(
    private val repository: InformationRepository
) {
    suspend operator fun invoke(
        lastId: Long? = null,
        district: String? = null,
        lat: Double? = null,
        lon: Double? = null,
        size: Int? = null
    ): Result<PagedResult<Center>> = repository.getCenters(
        lastId = lastId,
        district = district,
        lat = lat,
        long = lon,
        size = size
    )
}