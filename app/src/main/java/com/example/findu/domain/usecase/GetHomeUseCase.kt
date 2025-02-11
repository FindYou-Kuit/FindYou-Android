package com.example.findu.domain.usecase

import com.example.findu.domain.model.HomeData
import com.example.findu.domain.repository.HomeRepository

class GetHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<HomeData> =
        homeRepository.getHome()
}