package com.example.findu.data.dataremote.util

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse

fun <T> BaseResponse<T>.handleBaseResponse(): Result<T> =
    when (this.code) {
        in 20000..29999, in 200..299 -> { 
            data?.let { Result.success(it) }
                ?: Result.failure(IllegalStateException("data is null"))
        }

        in 40400..40499, in 400..499 -> { // 클라이언트 에러
            Result.failure(Exception("Client error : ${this.message}"))
        }

        in 50000..50099, in 500..599 -> { // 서버 에러
            Result.failure(Exception("Server error : ${this.message}"))
        }

        else -> {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }
    }

fun <T> NullableBaseResponse<T>.handleBaseResponse(): Result<T?> =
    when (this.code) {
        in 20000..29999, in 200..299 -> {
            Result.success(this.data)
        }

        in 40400..40499, in 400..499 -> {
            Result.failure(Exception("Client error : ${this.message}"))
        }

        in 50000..50099, in 500..599 -> {
            Result.failure(Exception("Server error : ${this.message}"))
        }

        else -> {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }
    }