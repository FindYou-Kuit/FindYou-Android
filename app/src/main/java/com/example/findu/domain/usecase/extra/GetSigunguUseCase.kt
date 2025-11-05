package com.example.findu.domain.usecase.extra

import com.example.findu.domain.repository.InformationRepository

class GetSigunguUseCase (
    private val repository: InformationRepository
) {
    suspend operator fun invoke(sidoId: Long): Result<List<String>> = repository.getSigungu(sidoId = sidoId)
}