package com.example.findu.data.dataremote.model.base

import kotlinx.serialization.SerialName

class NullableBaseResponse<T>(
    @SerialName("success") val success: Boolean,
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: T?
)