package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.domain.repository.InterestRepository
import javax.inject.Inject

class InterestRepositoryImpl @Inject constructor(
    private val interestRemoteDataSource: InterestRemoteDataSource
) : InterestRepository {
    override suspend fun getInterestProtectingAnimals(id: Long): Result<Unit> =
        runCatching {
            interestRemoteDataSource.getInterestProtectingAnimals(id).handleBaseResponse()
        }

    override suspend fun getInterestReportAnimals(id: Long): Result<Unit> =
        runCatching {
            interestRemoteDataSource.getInterestReportAnimals(id).handleBaseResponse()
        }

}