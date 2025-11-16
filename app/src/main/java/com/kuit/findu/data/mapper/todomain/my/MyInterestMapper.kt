package com.kuit.findu.data.mapper.todomain.my

import com.kuit.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.kuit.findu.domain.model.my.MyInterestData

fun MyInterestResponseDto.toDomain(): MyInterestData =
    MyInterestData(
        interestAnimals = interestAnimals.map {
            MyInterestData.InterestAnimal(
                reportId = it.reportId,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title?: "",
                tag = it.tag,
                date = it.date,
                address = it.address
            )
        },
        isLast = isLast,
        lastId = lastId
    )