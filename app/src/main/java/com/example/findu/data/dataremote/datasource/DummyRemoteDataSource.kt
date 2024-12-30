package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.response.DummyResponseDto

interface DummyRemoteDataSource {
    suspend fun dummy(): DummyResponseDto
}