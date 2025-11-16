package com.kuit.findu.data.mapper.todomain.my

import com.kuit.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.kuit.findu.domain.model.my.MyProfileData

fun MyNickNameResponseDto.toDomain(): MyProfileData =
    MyProfileData(
        nickname = nickname,
        profileImage = profileImage
    )