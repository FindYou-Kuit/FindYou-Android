package com.example.findu.domain.model.extra

interface HomeExtraDataType

data class Department(
    val name: String,
    val district: String,
    val phone: String
) : HomeExtraDataType

data class VolunteerWork(
    val institution: String,
    val recruitmentPeriod: String,
    val address: String,
    val workPeriod: String,
    val workTime: String,
    val webLink: String
) : HomeExtraDataType


data class Center(
    val jurisdiction: List<String>,
    val centerName: String,
    val phoneNumber: String,
    val address: String
)