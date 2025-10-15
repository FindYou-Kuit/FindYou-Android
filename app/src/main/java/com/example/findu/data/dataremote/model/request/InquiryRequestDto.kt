package com.example.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostInquiryRequestDto(
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("category")
    val category: List<String>
)