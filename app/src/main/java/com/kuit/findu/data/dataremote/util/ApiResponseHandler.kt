package com.kuit.findu.data.dataremote.util

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse

fun <T> BaseResponse<T>.handleBaseResponse(): Result<T> =
    when (this.code) {
        in 200..299 -> { // 성공
            Result.success(this.data)
        }

        401 -> {
            Result.failure(AuthenticationException())
        }

        in 400..499 -> { // 클라이언트 에러
            Result.failure(Exception("Client error : ${this.message}"))
        }

        in 500..599 -> { // 서버 에러
            Result.failure(Exception("Server error : ${this.message}"))
        }

        else -> {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }
    }

fun <T> NullableBaseResponse<T>.handleBaseResponse(): Result<T?> =
    when (this.code) {
        in 200..299 -> { // 성공
            Result.success(this.data)
        }

        in 400..499 -> { // 클라이언트 에러
            Result.failure(Exception("Client error : ${this.message}"))
        }

        in 500..599 -> { // 서버 에러
            Result.failure(Exception("Server error : ${this.message}"))
        }

        else -> {
            Result.failure(Exception("Unknown error : ${this.message}"))
        }
    }