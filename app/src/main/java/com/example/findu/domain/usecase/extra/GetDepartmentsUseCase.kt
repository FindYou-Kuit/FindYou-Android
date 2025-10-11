package com.example.findu.domain.usecase.extra

import com.example.findu.domain.repository.InformationRepository

class GetDepartmentsUseCase(
    private val repository: InformationRepository
) {
    suspend operator fun invoke(
        district: String? = null,
        lastId: Long? = Long.MAX_VALUE
    ) = repository.getDepartments(
        district = district,
        lastId = lastId
    )
}