package com.example.findu.data.mapper.todomain.my

import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.domain.model.my.MyProfileData

fun MyNickNameResponseDto.toDomain(): MyProfileData =
    MyProfileData(
        nickname = nickname,
        profileImage = profileImage
    )