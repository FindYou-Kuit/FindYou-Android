package com.kuit.findu.data.mapper.torequest

import com.kuit.findu.data.dataremote.model.request.GuestLoginRequestDto


fun String.toRequestDto() =
    GuestLoginRequestDto(deviceId = this)