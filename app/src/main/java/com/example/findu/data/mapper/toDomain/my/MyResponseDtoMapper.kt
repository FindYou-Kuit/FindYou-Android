package com.example.findu.data.mapper.toDomain.my

import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.model.my.MyViewedAnimalData

fun MyInterestResponseDto.toDomain(): MyInterestData {
    return MyInterestData(
        interestAnimals = interestAnimals.map {
            MyInterestData.InterestAnimal(
                reportId = it.reportId,
                date = it.date,
                address = it.address,
                tag = it.tag,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title
            )
        },
        isLast = isLast,
        lastId = lastId
    )
}


fun MyReportHistoryResponseDto.toDomain(): MyReportHistoryData {
    return MyReportHistoryData(
        reports = reports.map {
            MyReportHistoryData.Report(
                date = it.date,
                location = it.location,
                reportId = it.reportId,
                tag = it.tag,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title,
                interest = it.interest
            )
        },
        isLast = isLast,
        lastId = lastId,
    )
}

fun MyViewedAnimalsResponseDto.toDomain(): MyViewedAnimalData {
    return MyViewedAnimalData(
        cards = cards.map {
            MyViewedAnimalData.Card(
                reportId = it.reportId,
                date = it.date,
                interest = it.interest,
                location = it.location,
                tag = it.tag,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title
            )
        },
        isLast = isLast,
        lastId = lastId,
    )
}