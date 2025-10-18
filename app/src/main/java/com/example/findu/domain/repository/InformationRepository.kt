package com.example.findu.domain.repository

import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.Department
import com.example.findu.domain.model.extra.PagedResult
import com.example.findu.domain.model.extra.Sido
import com.example.findu.domain.model.extra.VolunteerWork

interface InformationRepository {

    suspend fun getVolunteers(
        lastId: Long? = Long.MAX_VALUE
    ): Result<PagedResult<VolunteerWork>>

    suspend fun getCenters(
        lastId: Long? = null,
        district: String? = null,
        lat: Double? = null,
        long: Double? = null,
        size: Int? = null
    ): Result<PagedResult<Center>>

    suspend fun getDepartments(
        district: String? = null,
        lastId: Long? = null
    ): Result<PagedResult<Department>>

    suspend fun getSido(): Result<List<Sido>>

    suspend fun getSigungu(sidoId: Long): Result<List<String>>

}