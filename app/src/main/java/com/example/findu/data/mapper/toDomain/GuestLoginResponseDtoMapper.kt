package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.example.findu.domain.model.GuestLoginData

fun GuestLoginResponseDto.toDomain(): GuestLoginData =
    GuestLoginData(
        userId = this.userId,
        accessToken = this.accessToken
    )