package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.service.InterestService
import javax.inject.Inject

class InterestRemoteDataSourceImpl @Inject constructor(
    private val interestService: InterestService
) : InterestRemoteDataSource {
    override suspend fun getInterestProtectingAnimals(id: Long) =
        interestService.getInterestProtectingAnimals(id)

    override suspend fun getInterestReportAnimals(id: Long) =
        interestService.getInterestReportAnimals(id)

    override suspend fun deleteInterestProtectingAnimals(reportId: Long): NullableBaseResponse<Unit> =
        interestService.deleteInterestProtectingAnimals(reportId)

    override suspend fun deleteInterestReportAnimals(reportId: Long): NullableBaseResponse<Unit> =
        interestService.deleteInterestReportAnimals(reportId)
}