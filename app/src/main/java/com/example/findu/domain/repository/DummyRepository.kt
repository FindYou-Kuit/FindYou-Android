package com.example.findu.domain.repository

import com.example.findu.data.dataremote.model.response.DummyResponseDto

interface DummyRepository{
    suspend fun dummy():Result<DummyResponseDto>
}