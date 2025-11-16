package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.response.my.MyInterestResponseDto

interface InterestRemoteDataSource {
    suspend fun getInterestAnimals(lastId: Long): NullableBaseResponse<MyInterestResponseDto>
    suspend fun registerInterestAnimal(reportId: Long): NullableBaseResponse<Unit>
    suspend fun deleteInterestAnimal(reportId: Long) : NullableBaseResponse<Unit>
}