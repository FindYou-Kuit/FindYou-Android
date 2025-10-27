package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.my.MyInterestData

interface InterestRepository {
    suspend fun getInterestAnimals(lastId: Long): Result<MyInterestData>
    suspend fun registerInterestAnimal(reportId: Long): Result<Unit>
    suspend fun deleteInterestAnimal(reportId: Long): Result<Unit>
}