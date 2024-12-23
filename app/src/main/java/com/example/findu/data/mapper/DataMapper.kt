package com.example.findu.data.mapper

import com.example.findu.data.dataremote.model.response.DummyResponseDto
import com.example.findu.domain.model.DummyData

fun DummyResponseDto.toDomainModel(): DummyData {
    return DummyData(
        description = this.dummy + "입니다 "
    )
}