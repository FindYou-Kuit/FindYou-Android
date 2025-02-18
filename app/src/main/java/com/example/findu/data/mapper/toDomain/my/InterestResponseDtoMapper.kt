package com.example.findu.data.mapper.todomain.my

import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.domain.model.my.MyInterestData

fun MyInterestResponseDto.toDomain(): MyInterestData {
    return MyInterestData(
        interestAnimals = interestAnimals.map {
            MyInterestData.InterestAnimal(
                animalId = it.animalId,
                date = it.date,
                interest = it.interest,
                location = it.location,
                tag = it.tag,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title
            )
        },
        isLast = isLast,
        lastInterestProtectId = lastInterestProtectId,
        lastInterestReportId = lastInterestReportId
    )
}
