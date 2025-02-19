package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.example.findu.data.dataremote.service.InterestService
import javax.inject.Inject

class InterestRemoteDataSourceImpl @Inject constructor(
    private val interestService: InterestService
) : InterestRemoteDataSource {
    override suspend fun getInterestProtectingAnimals(id: Long) =
        interestService.getInterestProtectingAnimals(id)


    override suspend fun getInterestReportAnimals(id: Long) =
        interestService.getInterestReportAnimals(id)

}