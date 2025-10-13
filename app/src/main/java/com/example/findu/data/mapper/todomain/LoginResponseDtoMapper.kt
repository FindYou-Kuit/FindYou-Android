package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.example.findu.domain.model.LoginData

fun LoginResponseDto.toDomain(): LoginData =
    LoginData(
        isFirstLogin = isFirstLogin,
        userInfo = userInfo?.toDomain()
    )