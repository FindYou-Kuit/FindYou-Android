package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.data.mapper.todomain.my.toDomain
import com.kuit.findu.domain.model.my.MyInterestData
import com.kuit.findu.domain.repository.InterestRepository
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