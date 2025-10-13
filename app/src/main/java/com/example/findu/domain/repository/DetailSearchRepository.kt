package com.example.findu.domain.repository

import com.example.findu.domain.model.search.DetailMissingData
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailWitnessData

interface DetailSearchRepository {

    suspend fun getDetailSearchProtect(reportId: Long): Result<DetailProtectData>

    suspend fun getDetailSearchMissing(reportId: Long): Result<DetailMissingData>

    suspend fun getDetailSearchWitness(reportId: Long): Result<DetailWitnessData>
}