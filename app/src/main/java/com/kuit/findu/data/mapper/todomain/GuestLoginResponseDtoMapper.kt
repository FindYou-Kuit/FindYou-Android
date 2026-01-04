package com.kuit.findu.data.mapper.todomain

import com.kuit.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.kuit.findu.domain.model.GuestLoginData

fun GuestLoginResponseDto.toDomain(): GuestLoginData =
    GuestLoginData(
        userId = this.userId,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )