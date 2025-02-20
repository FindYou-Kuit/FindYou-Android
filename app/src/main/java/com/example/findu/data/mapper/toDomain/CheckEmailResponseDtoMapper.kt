package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.CheckEmailResponseDto
import com.example.findu.domain.model.CheckEmailData

fun CheckEmailResponseDto.toDomain() = CheckEmailData(
    isDuplicateEmail = isDuplicateEmail
)