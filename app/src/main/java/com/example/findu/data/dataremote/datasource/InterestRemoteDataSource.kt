package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.NullableBaseResponse

interface InterestRemoteDataSource {
    suspend fun getInterestProtectingAnimals(id: Long) : NullableBaseResponse<Int>
    suspend fun getInterestReportAnimals(id: Long) : NullableBaseResponse<Int>
}