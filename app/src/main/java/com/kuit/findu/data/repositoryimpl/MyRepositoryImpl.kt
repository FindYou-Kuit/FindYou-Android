package com.kuit.findu.data.repositoryimpl

import android.net.Uri
import com.kuit.findu.FindUApp
import com.kuit.findu.data.dataremote.datasource.MyRemoteDataSource
import com.kuit.findu.data.dataremote.model.request.PatchNicknameRequestDto
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.data.mapper.todomain.my.toDomain
import com.kuit.findu.domain.model.my.MyInterestData
import com.kuit.findu.domain.model.my.MyProfileData
import com.kuit.findu.domain.model.my.MyProfileImageUpdate
import com.kuit.findu.domain.model.my.MyReportHistoryData
import com.kuit.findu.domain.model.my.MyViewedAnimalData
import com.kuit.findu.domain.repository.MyRepository
import com.kuit.findu.presentation.util.UriUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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
            myRemoteDataSource
                .patchNickname(PatchNicknameRequestDto(newNickname))
                .handleBaseResponse()
                .getOrThrow()
        }

    override suspend fun getNickname(): Result<MyProfileData> =
        runCatching {
            myRemoteDataSource.getNickname()
                .handleBaseResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun patchProfileImage(update: MyProfileImageUpdate): Result<Unit> =
        runCatching {
            when (update) {
                is MyProfileImageUpdate.FilePath -> {
                    val file = File(update.path)
                    myRemoteDataSource.patchProfileImage(
                        profileImageFile = file,
                        defaultImageName = null
                    ).handleBaseResponse().getOrThrow()
                }

                is MyProfileImageUpdate.Default -> {
                    myRemoteDataSource.patchProfileImage(
                        profileImageFile = null,
                        defaultImageName = update.name
                    ).handleBaseResponse().getOrThrow()
                }
            }
        }

}