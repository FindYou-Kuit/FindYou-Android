package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.model.HomeData
import com.kuit.findu.domain.repository.HomeRepository

class GetHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(lat: Double?=null,lon:Double?=null): Result<HomeData> =
        homeRepository.getHome(lat,lon).map { homeData ->
            homeData
        }
}