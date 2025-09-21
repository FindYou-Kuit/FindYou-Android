package com.example.findu.domain.model.extra

data class DepartmentList(
    val departments: List<Department>,
    val lastId: Int,
    val isLast: Boolean
)


data class Department(
    val name: String,
    val district: String,
    val phone: String
)