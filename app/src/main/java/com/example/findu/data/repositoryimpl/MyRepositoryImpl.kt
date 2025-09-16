package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.toDomain.my.toDomain
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyProfileData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.model.my.MyViewedAnimalData
import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource
) : MyRepository {
    override suspend fun getMyInterest(
        lastId: Long,
    ): Result<MyInterestData> =
        runCatching {
            myRemoteDataSource.getInterestAnimals(
                lastId = lastId,
            ).handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun getMyReportHistory(lastReportId: Long): Result<MyReportHistoryData> =
        runCatching {
            myRemoteDataSource.getReportHistory(lastReportId = lastReportId)
                .handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun getMyViewedAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): Result<MyViewedAnimalData> =
        runCatching {
            myRemoteDataSource.getViewedAnimals(
                lastReportId = lastReportId,
                lastProtectId = lastProtectId
            ).handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun deleteUser(): Result<Unit> =
        runCatching {
            myRemoteDataSource.deleteUser().handleBaseResponse().getOrThrow()
        }

    override suspend fun patchNickname(newNickname: String): Result<Unit> =
        runCatching {
            myRemoteDataSource.patchNickname(newNickname).handleBaseResponse().getOrThrow()
        }

    override suspend fun getNickname(): Result<MyProfileData> =
        runCatching {
            myRemoteDataSource.getNickname()
                .handleBaseResponse()
                .getOrThrow()
                .let { dto ->
                    MyProfileData(
                        nickname = dto.nickname,
                        profileImage = dto.profileImage
                    )
                }
        }
}