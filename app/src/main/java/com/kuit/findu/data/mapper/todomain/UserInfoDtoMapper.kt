package com.kuit.findu.data.mapper.todomain

import com.kuit.findu.data.dataremote.model.response.auth.UserInfoDto
import com.kuit.findu.domain.model.UserInfo

fun UserInfoDto.toDomain(): UserInfo =
    UserInfo(
        userId = this.userId,
        nickname = this.nickname,
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )