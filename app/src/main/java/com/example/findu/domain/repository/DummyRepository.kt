package com.example.findu.domain.repository

import com.example.findu.domain.model.DummyData

interface DummyRepository{
    suspend fun dummy():Result<DummyData>
}