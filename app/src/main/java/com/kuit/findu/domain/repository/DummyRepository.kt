package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.DummyData

interface DummyRepository{
    suspend fun dummy():Result<DummyData>
}