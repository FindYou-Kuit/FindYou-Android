package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.InterestRemoteDataSource
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.MyInterestRequestDto
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.service.InterestService
import javax.inject.Inject

class InterestRemoteDataSourceImpl @Inject constructor(
    private val interestService: InterestService,
) : InterestRemoteDataSource {
    override suspend fun getInterestAnimals(lastId: Long) =
        interestService.getInterestAnimals(lastId)

    override suspend fun registerInterestAnimal(reportId: Long) =
        interestService.registerInterestAnimal(MyInterestRequestDto(reportId))

    override suspend fun deleteInterestAnimal(reportId: Long): NullableBaseResponse<Unit> =
        interestService.deleteInterestAnimal(reportId)


}