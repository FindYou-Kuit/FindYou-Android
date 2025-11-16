package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.response.DummyResponseDto

interface DummyRemoteDataSource {
    suspend fun dummy(): DummyResponseDto
}