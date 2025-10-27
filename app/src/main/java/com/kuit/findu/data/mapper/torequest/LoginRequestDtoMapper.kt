package com.kuit.findu.data.mapper.torequest

import com.kuit.findu.data.dataremote.model.request.LoginRequestDto
import com.kuit.findu.domain.model.LoginInfo


fun LoginInfo.toRequestDto() =
    LoginRequestDto(kakaoId = this.kakaoId, deviceId = this.deviceId)