package com.example.findu.domain.usecase.extra

import com.example.findu.domain.repository.InformationRepository

class GetVolunteersUseCase(
    private val repository: InformationRepository
) {
    suspend operator fun invoke(lastId: Long? = Long.MAX_VALUE) =
        repository.getVolunteers(lastId)
}