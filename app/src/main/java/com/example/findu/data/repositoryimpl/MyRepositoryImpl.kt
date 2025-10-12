package com.example.findu.data.repositoryimpl

import android.net.Uri
import com.example.findu.FindUApp
import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.model.request.PatchNicknameRequestDto
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.my.toDomain
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyProfileData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.model.my.MyViewedAnimalData
import com.example.findu.domain.repository.MyRepository
import com.example.findu.presentation.util.UriUtil
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


    override suspend fun patchProfileImageFile(imagePath: String): Result<Unit> =
        runCatching {
            val file = File(imagePath)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("profileImageFile", file.name, requestFile)
            myRemoteDataSource.patchProfileImageFile(multipartBody).handleBaseResponse().getOrThrow()
        }

    override suspend fun patchProfileImageDefault(defaultProfileImageName: String): Result<Unit> =
        runCatching {
            val requestBody = defaultProfileImageName.toRequestBody("text/plain".toMediaType())
            myRemoteDataSource.patchProfileImageDefault(requestBody).handleBaseResponse().getOrThrow()
        }
}