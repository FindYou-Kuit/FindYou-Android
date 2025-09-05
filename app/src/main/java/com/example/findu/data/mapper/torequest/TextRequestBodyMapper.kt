package com.example.findu.data.mapper.torequest

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun String.toPlainTextRequestBody(): RequestBody =
    toRequestBody("text/plain".toMediaTypeOrNull())

fun File.toImageMultipart(name: String): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        name,
        this.name,
        asRequestBody("image/*".toMediaTypeOrNull())
    )