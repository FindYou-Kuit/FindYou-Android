package com.example.findu.data.dataremote.model.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GptRequestDto(
    @SerializedName("max_tokens")
    val maxTokens: Int = 300,
    @SerializedName("model")
    val model: String = "gpt-4o",
    @SerializedName("messages")
    val messages: List<RequestMessage> = listOf(),
)
@Serializable
data class RequestMessage(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("role")
    val role: String
)

@Serializable
data class Content(
    @SerializedName("type")
    val type: String,
    @SerializedName("image_url")
    var imageUrl: String? = null, // 이미지 URL이 있을 때만 사용
    @SerializedName("text")
    var text: String? = null // 텍스트가 있을 때만 사용
)

