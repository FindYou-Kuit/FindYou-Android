package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.repository.InterestRepository
import javax.inject.Inject

class InterestRepositoryImpl @Inject constructor(
    private val interestRemoteDataSource: InterestRemoteDataSource
) : InterestRepository {
    override suspend fun getInterestAnimals(lastId: Long): Result<MyInterestData> =
        runCatching {
            val dto: MyInterestResponseDto =
                interestRemoteDataSource
                    .getInterestAnimals(lastId)
                    .handleBaseResponse()
                    .getOrThrow()
                    ?: error("Empty response body")


            MyInterestData(
                interestAnimals = dto.interestAnimals.map {
                    MyInterestData.InterestAnimal(
                        reportId = it.reportId,
                        thumbnailImageUrl = it.thumbnailImageUrl,
                        title = it.title,
                        tag = it.tag,
                        date = it.date,
                        address = it.address
                    )
                },
                isLast = dto.isLast,
                lastId = dto.lastId
            )
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