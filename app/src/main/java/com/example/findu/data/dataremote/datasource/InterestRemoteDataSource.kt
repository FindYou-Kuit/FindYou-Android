package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto

interface InterestRemoteDataSource {
    suspend fun getInterestAnimals(lastId: Long): NullableBaseResponse<MyInterestResponseDto>
    suspend fun registerInterestAnimal(reportId: Long): NullableBaseResponse<Unit>
    suspend fun deleteInterestAnimal(reportId: Long) : NullableBaseResponse<Unit>
}