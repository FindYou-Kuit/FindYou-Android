package com.example.findu.data.dataremote.model.response.information

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DepartmentsResponseDto(
    @SerialName("departments")
    val departments: List<DepartmentDto>,

    @SerialName("lastId")
    val lastId: Long?,

    @SerialName("isLast")
    val isLast: Boolean
)

@Serializable
data class DepartmentDto(
    @SerialName("departmentName")
    val departmentName: String,

    @SerialName("district")
    val district: String,

    @SerialName("phoneNumber")
    val phoneNumber: String
)