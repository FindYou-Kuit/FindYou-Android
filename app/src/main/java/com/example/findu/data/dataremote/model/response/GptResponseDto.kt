package com.example.findu.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GptResponseDto(
    @SerialName("choices")
    val choices: List<Choice>,
    @SerialName("created")
    val created: Int,
    @SerialName("id")
    val id: String,
    @SerialName("model")
    val model: String,
    @SerialName("object")
    val `object`: String,
    @SerialName("system_fingerprint")
    val systemFingerprint: String,
    @SerialName("usage")
    val usage: Usage
)

@Serializable
data class Choice(
    @SerialName("finish_reason")
    val finishReason: String,
    @SerialName("index")
    val index: Int,
    @SerialName("logprobs")
    val logprobs: String?,
    @SerialName("message")
    val message: ResponseMessage
)

@Serializable
data class Usage(
    @SerialName("completion_tokens")
    val completionTokens: Int,
    @SerialName("completion_tokens_details")
    val completionTokensDetails: CompletionTokensDetails,
    @SerialName("prompt_tokens")
    val promptTokens: Int,
    @SerialName("prompt_tokens_details")
    val promptTokensDetails: PromptTokensDetails,
    @SerialName("total_tokens")
    val totalTokens: Int
)

@Serializable
data class CompletionTokensDetails(
    @SerialName("accepted_prediction_tokens")
    val acceptedPredictionTokens: Int,
    @SerialName("audio_tokens")
    val audioTokens: Int,
    @SerialName("reasoning_tokens")
    val reasoningTokens: Int,
    @SerialName("rejected_prediction_tokens")
    val rejectedPredictionTokens: Int
)

@Serializable
data class ResponseMessage(
    @SerialName("content")
    val content: String,
    @SerialName("refusal")
    val refusal: String?,
    @SerialName("role")
    val role: String
)

@Serializable
data class PromptTokensDetails(
    @SerialName("audio_tokens")
    val audioTokens: Int,
    @SerialName("cached_tokens")
    val cachedTokens: Int
)