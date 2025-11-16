package com.kuit.findu.domain.usecase.extra

import com.kuit.findu.domain.repository.InformationRepository

class GetSigunguUseCase (
    private val repository: InformationRepository
) {
    suspend operator fun invoke(sidoId: Long): Result<List<String>> = repository.getSigungu(sidoId = sidoId)
}