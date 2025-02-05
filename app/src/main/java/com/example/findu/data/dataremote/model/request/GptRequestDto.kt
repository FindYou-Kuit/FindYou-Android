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
    val messages: List<RequestMessage> = requestMessages,
) {
    companion object {
        val imageContent = Content(
            type = "image_url"
        )

        val textContent = Content(
            type = "Analyze the pet in this image and return a JSON response with:\n" +
                    "1. \"species\": The species of the pet (dog, cat, or other).\n" +
                    "2. \"breed\": The exact breed name (only one).\n" +
                    "3. \"fur_color\": The detected fur color, but it must be one of these: 검은색, 노란색, 갈색, 하얀색, 회색, 적색, 점박이, 줄무늬, 기타.\n" +
                    "\n" +
                    "Return the response strictly in JSON format **without using ```json ``` or any extra text.**"
        )
        val requestMessages = listOf(
            RequestMessage(
                content = listOf(
                    imageContent,
                    textContent
                ),
                role = "user"
            )
        )
    }
}
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

