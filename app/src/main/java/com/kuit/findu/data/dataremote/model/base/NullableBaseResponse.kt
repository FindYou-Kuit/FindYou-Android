package com.kuit.findu.data.dataremote.model.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class NullableBaseResponse<T>(
    @SerialName("success") val success: Boolean,
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: T?
)