package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.auth.UserInfoDto
import com.example.findu.domain.model.UserInfo

fun UserInfoDto.toDomain(): UserInfo =
    UserInfo(
        userId = this.userId,
        nickname = this.nickname,
        accessToken = this.accessToken
    )