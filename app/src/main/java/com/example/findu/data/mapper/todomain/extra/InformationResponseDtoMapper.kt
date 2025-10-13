package com.example.findu.data.mapper.todomain.extra


import com.example.findu.data.dataremote.model.response.information.CentersResponseDto
import com.example.findu.data.dataremote.model.response.information.DepartmentsResponseDto
import com.example.findu.data.dataremote.model.response.information.VolunteersResponseDto
import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.Department
import com.example.findu.domain.model.extra.PagedResult
import com.example.findu.domain.model.extra.VolunteerWork

fun VolunteersResponseDto.toDomain(): PagedResult<VolunteerWork> =
    PagedResult(
        items = volunteerWorks.map {
            VolunteerWork(
                institution = it.institution,
                recruitmentPeriod = it.recruitmentPeriod,
                address = it.address,
                workPeriod = it.workPeriod,
                workTime = it.workTime,
                webLink = it.webLink
            )
        },
        lastId = lastId,
        isLast = isLast
    )

fun CentersResponseDto.toDomain(): PagedResult<Center> =
    PagedResult(
        items = centers.map {
            Center(
                jurisdiction = it.jurisdiction,
                centerName = it.centerName,
                phoneNumber = it.phoneNumber,
                address = it.address
            )
        },
        lastId = lastId,
        isLast = isLast
    )

fun DepartmentsResponseDto.toDomain(): PagedResult<Department> =
    PagedResult(
        items = departments.map {
            Department(
                name = it.departmentName,
                district = it.district,
                phone = it.phoneNumber
            )
        },
        lastId = lastId,
        isLast = isLast
    )
