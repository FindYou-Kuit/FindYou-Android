package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.my.toDomain
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.repository.InterestRepository
import javax.inject.Inject

class InterestRepositoryImpl @Inject constructor(
    private val interestRemoteDataSource: InterestRemoteDataSource,
) : InterestRepository {
    override suspend fun getInterestAnimals(lastId: Long): Result<MyInterestData> =
        runCatching {
            val dto = interestRemoteDataSource
                .getInterestAnimals(lastId)
                .handleBaseResponse()
                .getOrThrow()
                ?: error("Empty response body")

            dto.toDomain()
        }

    override suspend fun registerInterestAnimal(reportId: Long): Result<Unit> =
        runCatching {
            interestRemoteDataSource.registerInterestAnimal(reportId).handleBaseResponse()
        }

    override suspend fun deleteInterestAnimal(reportId: Long): Result<Unit> =
        runCatching {
            interestRemoteDataSource.deleteInterestAnimal(reportId).handleBaseResponse()
        }

}