package com.kuit.findu.domain.usecase.extra

import com.kuit.findu.domain.repository.InformationRepository

class GetVolunteersUseCase(
    private val repository: InformationRepository
) {
    suspend operator fun invoke(lastId: Long? = Long.MAX_VALUE) =
        repository.getVolunteers(lastId)
}