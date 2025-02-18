package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.my.toDomain
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource
) : MyRepository {
    override suspend fun getMyInterest(
        lastReportId: Long,
        lastProtectId: Long,
    ): Result<MyInterestData> =
        runCatching {
            myRemoteDataSource.getInterestAnimals(
                lastReportId = lastReportId,
                lastProtectId = lastProtectId
            ).handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun getMyReportHistory(lastReportId: Long): Result<MyReportHistoryData> =
        runCatching {
            myRemoteDataSource.getReportHistory(lastReportId = lastReportId)
                .handleBaseResponse().getOrThrow().toDomain()
        }
}