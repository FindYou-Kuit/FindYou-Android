package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.example.findu.domain.model.LoginData
import com.example.findu.domain.model.UserInfo

fun LoginResponseDto.toDomain(): LoginData =
    LoginData(
        isFirstLogin = isFirstLogin,
        userInfo = userInfo?.let {
            UserInfo(
                userId = it.userId,
                nickname = it.nickname,
                accessToken = it.accessToken
            )
        }
    )