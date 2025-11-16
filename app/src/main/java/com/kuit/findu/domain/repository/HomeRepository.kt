package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.HomeData

interface HomeRepository {
    suspend fun getHome(
        lat: Double?,
        lon: Double?
    ): Result<HomeData>
}