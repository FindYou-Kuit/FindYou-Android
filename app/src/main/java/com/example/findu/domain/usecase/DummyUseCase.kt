package com.example.findu.domain.usecase

import com.example.findu.domain.repository.DummyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyUseCase @Inject constructor(
    private val dummyRepository: DummyRepository
) {
    suspend operator fun invoke() = dummyRepository.dummy()
}