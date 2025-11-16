package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.search.DetailMissingData
import com.kuit.findu.domain.model.search.DetailProtectData
import com.kuit.findu.domain.model.search.DetailWitnessData

interface DetailSearchRepository {

    suspend fun getDetailSearchProtect(reportId: Long): Result<DetailProtectData>

    suspend fun getDetailSearchMissing(reportId: Long): Result<DetailMissingData>

    suspend fun getDetailSearchWitness(reportId: Long): Result<DetailWitnessData>
}