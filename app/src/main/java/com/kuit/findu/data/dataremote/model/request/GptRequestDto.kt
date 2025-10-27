package com.kuit.findu.data.dataremote.model.request

import com.kuit.findu.data.dataremote.model.request.GptRequestConstants.GPT_MAX_TOKENS
import com.kuit.findu.data.dataremote.model.request.GptRequestConstants.GPT_MODEL
import com.kuit.findu.data.dataremote.model.request.GptRequestConstants.IMAGE_URL_TYPE
import com.kuit.findu.data.dataremote.model.request.GptRequestConstants.ROLE_USER
import com.kuit.findu.data.dataremote.model.request.GptRequestConstants.TEXT_TYPE
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
        var textContent = Content(type = TEXT_TYPE, text = "")

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
    fun getPromptText(
        dogList: List<String>,
        catList: List<String>,
        etcList: List<String>
    ): String = PROMPT_TEXT_INTRODUCE +
            "If the species is \"강아지\": ${dogList.joinToString(", ")}  \n" +
            "If the species is \"고양이\": ${catList.joinToString(", ")}  \n" +
            "If the species is \"기타\": ${etcList.joinToString(", ")}  \n" +
            PROMPT_OUTPUT_TEXT


    const val GPT_MODEL = "gpt-4o"
    const val GPT_MAX_TOKENS = 300
    const val ROLE_USER = "user"
    private const val PROMPT_TEXT_INTRODUCE = "Generate a response in the following format:  \n" +
            "Species,Breed,Color1,Color2,Color3,...  \n" +
            "- The species must be one of the following: \"강아지\", \"고양이\", \"기타\".  \n" +
            "- The breed must be exactly one, and it must match the species category:  \n"

    private const val PROMPT_OUTPUT_TEXT =
        "- Colors must be one or more, separated by commas (\",\").  \n" +
                "The color must be chosen from the following fixed list: 검은색, 노란색, 갈색, 하얀색, 회색, 적색, 점박이, 줄무늬, 기타.  \n" +
                "- There should be no spaces between commas in the color list.  \n" +
                "**Example input & expected response:**  \n" +
                "강아지,골든 리트리버,노란색  \n" +
                "고양이,러시안 블루,회색,검은색  \n" +
                "기타축종,기타,흰색  \n"
    const val IMAGE_URL_TYPE = "image_url"
    const val TEXT_TYPE = "text"
}