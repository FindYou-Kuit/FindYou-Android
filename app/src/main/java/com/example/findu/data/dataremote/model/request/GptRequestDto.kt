package com.example.findu.data.dataremote.model.request

import com.example.findu.data.dataremote.model.request.GptRequestConstants.GPT_MAX_TOKENS
import com.example.findu.data.dataremote.model.request.GptRequestConstants.GPT_MODEL
import com.example.findu.data.dataremote.model.request.GptRequestConstants.IMAGE_URL_TYPE
import com.example.findu.data.dataremote.model.request.GptRequestConstants.PROMPT_TEXT
import com.example.findu.data.dataremote.model.request.GptRequestConstants.ROLE_USER
import com.example.findu.data.dataremote.model.request.GptRequestConstants.TEXT_TYPE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GptRequestDto(
    @SerialName("max_tokens")
    var maxTokens: Int = GPT_MAX_TOKENS,
    @SerialName("messages")
    val messages: List<RequestMessage> = requestMessage,
    @SerialName("model")
    var model: String = GPT_MODEL,
) {
    companion object {
        val imageContent = Content(type = IMAGE_URL_TYPE)
        private val textContent = Content(type = TEXT_TYPE, text = PROMPT_TEXT)

        val requestMessage = listOf(
                RequestMessage(
                    content = listOf(
                        imageContent,
                        textContent
                    ),
                )
            )
    }
}
@Serializable
data class RequestMessage(
    @SerialName("content")
    val content: List<Content>,
    @SerialName("role")
    val role: String = ROLE_USER
)

@Serializable
data class Content(
    @SerialName("type")
    val type: String,
    @SerialName("image_url")
    var imageUrl: ImageUrl? = null, // 이미지 URL이 있을 때만 사용
    @SerialName("text")
    var text: String? = null // 텍스트가 있을 때만 사용
)

@Serializable
data class ImageUrl(
    @SerialName("url")
    val url: String
)

object GptRequestConstants {
    const val GPT_MODEL = "gpt-4o"
    const val GPT_MAX_TOKENS = 300
    const val ROLE_USER = "user"
    const val PROMPT_TEXT = "Analyze the pet in this image and return a JSON response with:\n" +
            "1. \"species\": The species of the pet (강아지, 고양이, or 기타).\n" +
            "2. \"breed\": The exact breed name (only one).\n" +
            "3. \"fur_color\": The detected fur color, return at least one of these(allow multiple): 검은색, 노란색, 갈색, 하얀색, 회색, 적색, 점박이, 줄무늬, 기타.\n" +
            "\n" +
            "Strictly format the response as comma-separated text** - **Do not include** any unnecessary symbols, line breaks, or formatting.\n" +
            "\n" +
            "### **Example Output(Only Korean)**\n" +
            "강아지,리트리버,노란색"
    const val IMAGE_URL_TYPE = "image_url"
    const val TEXT_TYPE = "text"
}