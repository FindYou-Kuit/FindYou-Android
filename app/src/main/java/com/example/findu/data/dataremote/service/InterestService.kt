package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.MyInterestRequestDto
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InterestService {

    @GET("/api/v2/users/me/interest-animals")
    suspend fun getInterestAnimals(
        @Query("lastId") lastId: Long
    ): NullableBaseResponse<MyInterestResponseDto>

    @POST("/api/v2/users/me/interest-animals")
    suspend fun registerInterestAnimal(
        @Body request: MyInterestRequestDto
    ) : NullableBaseResponse<Unit>

    @DELETE("/api/v2/users/me/interest-animals")
    suspend fun deleteInterestAnimal(
        @Query("reportId") reportId : Long
    ) : NullableBaseResponse<Unit>

}