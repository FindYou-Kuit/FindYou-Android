package com.kuit.findu.domain.usecase.extra

import com.kuit.findu.domain.repository.InformationRepository

class GetDepartmentsUseCase(
    private val repository: InformationRepository
) {
    suspend operator fun invoke(
        district: String? = null,
        lastId: Long? = null
    ) = repository.getDepartments(
        district = district,
        lastId = lastId
    )
}