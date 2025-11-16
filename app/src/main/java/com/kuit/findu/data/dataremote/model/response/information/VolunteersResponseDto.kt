package com.kuit.findu.data.dataremote.model.response.information

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolunteersResponseDto(
    @SerialName("volunteerWorks")
    val volunteerWorks: List<VolunteerWorkDto>,
    @SerialName("lastId")
    val lastId: Long,
    @SerialName("isLast")
    val isLast: Boolean
)

@Serializable
data class VolunteerWorkDto(
    @SerialName("institution")
    val institution: String,
    @SerialName("recruitmentPeriod")
    val recruitmentPeriod: String,
    @SerialName("address")
    val address: String,
    @SerialName("workPeriod")
    val workPeriod: String,
    @SerialName("workTime")
    val workTime: String,
    @SerialName("webLink")
    val webLink: String
)
