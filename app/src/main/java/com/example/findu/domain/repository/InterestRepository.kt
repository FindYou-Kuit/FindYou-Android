package com.example.findu.domain.repository

interface InterestRepository {
    suspend fun getInterestProtectingAnimals(id: Long): Result<Unit>
    suspend fun getInterestReportAnimals(id: Long): Result<Unit>
}