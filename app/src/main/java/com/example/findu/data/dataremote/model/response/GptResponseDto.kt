package com.example.findu.data.dataremote.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GptResponseDto(
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("created")
    val created: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("object")
    val `object`: String,
    @SerializedName("system_fingerprint")
    val systemFingerprint: String,
    @SerializedName("usage")
    val usage: Usage
)

@Serializable
data class Choice(
    @SerializedName("finish_reason")
    val finishReason: String,
    @SerializedName("index")
    val index: Int,
    @SerializedName("logprobs")
    val logprobs: String?,
    @SerializedName("message")
    val message: ResponseMessage
)

@Serializable
data class Usage(
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    @SerializedName("completion_tokens_details")
    val completionTokensDetails: CompletionTokensDetails,
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    @SerializedName("prompt_tokens_details")
    val promptTokensDetails: PromptTokensDetails,
    @SerializedName("total_tokens")
    val totalTokens: Int
)

@Serializable
data class CompletionTokensDetails(
    @SerializedName("accepted_prediction_tokens")
    val acceptedPredictionTokens: Int,
    @SerializedName("audio_tokens")
    val audioTokens: Int,
    @SerializedName("average_ppl")
    val reasoningTokens: Int,
    @SerializedName("cached_tokens")
    val rejectedPredictionTokens: Int
)

@Serializable
data class ResponseMessage(
    @SerializedName("content")
    val content: String,
    @SerializedName("refusal")
    val refusal: String?,
    @SerializedName("role")
    val role: String
)

@Serializable
data class PromptTokensDetails(
    @SerializedName("accepted_tokens")
    val audioTokens: Int,
    @SerializedName("rejected_tokens")
    val cachedTokens: Int
)