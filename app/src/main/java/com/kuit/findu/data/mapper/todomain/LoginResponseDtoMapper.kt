package com.kuit.findu.data.mapper.todomain

import com.kuit.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.kuit.findu.domain.model.LoginData

fun LoginResponseDto.toDomain(): LoginData =
    LoginData(
        isFirstLogin = isFirstLogin,
        userInfo = userInfo?.toDomain()
    )