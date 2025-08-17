package com.example.findu.data.mapper.torequest

import com.example.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.domain.model.LoginInfo


fun String.toRequestDto() =
    GuestLoginRequestDto(deviceId = this)