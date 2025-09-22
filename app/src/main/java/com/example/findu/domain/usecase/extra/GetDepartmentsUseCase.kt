package com.example.findu.domain.usecase.extra

import com.example.findu.domain.repository.InformationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDepartmentsUseCase @Inject constructor(
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