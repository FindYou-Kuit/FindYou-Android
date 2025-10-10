package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.my.toDomain
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyProfileData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.model.my.MyViewedAnimalData
import com.example.findu.domain.repository.MyRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myRemoteDataSource: MyRemoteDataSource,
) : MyRepository {
    override suspend fun getMyInterest(
        lastId: Long,
    ): Result<MyInterestData> =
        runCatching {
            myRemoteDataSource.getInterestAnimals(
                lastId = lastId,
            ).handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun getMyReportHistory(lastId: Long): Result<MyReportHistoryData> =
        runCatching {
            myRemoteDataSource.getReportHistory(lastId = lastId)
                .handleBaseResponse().getOrThrow().toDomain()
        }

    override suspend fun getMyViewedAnimals(
        lastId: Long,
    ): Result<MyViewedAnimalData> =
        runCatching {
            myRemoteDataSource.getViewedAnimals(
                lastId = lastId,
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
                .toDomain()
        }


    override suspend fun patchProfileImageFile(file: MultipartBody.Part): Result<Unit> =
        runCatching {
            myRemoteDataSource.patchProfileImageFile(file).handleBaseResponse().getOrThrow()
        }

    override suspend fun patchProfileImageDefault(defaultProfileImageName: RequestBody): Result<Unit> =
        runCatching {
            myRemoteDataSource.patchProfileImageDefault(defaultProfileImageName)
                .handleBaseResponse().getOrThrow()
        }
}