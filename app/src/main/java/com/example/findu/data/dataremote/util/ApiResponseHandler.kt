package com.example.findu.data.dataremote.util

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse

fun <T> BaseResponse<T>.handleBaseResponse(): Result<T> =
    when (this.code) {
        in 20000..29999 -> {
            Result.success(this.data)
        }

        in 40400..40499 -> {
            Result.failure(Exception("Client error : ${this.message}"))
        }

        in 50000..50099 -> {
            Result.failure(Exception("Server error : ${this.message}"))
        }

        else -> {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }
    }

fun <T> NullableBaseResponse<T>.handleBaseResponse(): Result<T?> =
    when (this.code) {
        in 2000..2999 -> {
            Result.success(this.data)
        }

        in 40400..40499 -> {
            Result.failure(Exception("Client error : ${this.message}"))
        }

        in 50000..50099 -> {
            Result.failure(Exception("Server error : ${this.message}"))
        }

        else -> {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }
    }