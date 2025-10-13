package com.example.findu.domain.usecase

import com.example.findu.domain.model.HomeData
import com.example.findu.domain.repository.HomeRepository

class GetHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(lat: Double?=null,lon:Double?=null): Result<HomeData> =
        homeRepository.getHome(lat,lon).map { homeData ->
            homeData
        }
}