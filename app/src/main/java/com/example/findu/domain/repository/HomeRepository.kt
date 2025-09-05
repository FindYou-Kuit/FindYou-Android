package com.example.findu.domain.repository

import com.example.findu.domain.model.HomeData

interface HomeRepository {
    suspend fun getHome(
        lat: Double?,
        lon: Double?
    ): Result<HomeData>
}