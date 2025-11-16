package com.kuit.findu.data.mapper

import com.kuit.findu.data.dataremote.model.response.DummyResponseDto
import com.kuit.findu.domain.model.DummyData

fun DummyResponseDto.toDomainModel(): DummyData {
    return DummyData(
        description = this.dummy + "입니다 "
    )
}