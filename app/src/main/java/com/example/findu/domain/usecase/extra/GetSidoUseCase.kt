package com.example.findu.domain.usecase.extra

import com.example.findu.domain.model.extra.Sido
import com.example.findu.domain.repository.InformationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSidoUseCase @Inject constructor(
    private val repository: InformationRepository
) {
    suspend operator fun invoke() :Result<List<Sido>> = repository.getSido()
}