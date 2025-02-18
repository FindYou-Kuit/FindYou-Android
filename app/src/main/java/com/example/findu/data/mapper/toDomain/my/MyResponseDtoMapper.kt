package com.example.findu.data.mapper.todomain.my

import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyReportHistoryData

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


fun MyReportHistoryResponseDto.toDomain(): MyReportHistoryData {
    return MyReportHistoryData(
        isLast = isLast,
        lastReportId = lastReportId,
        reports = reports.map {
            MyReportHistoryData.Report(
                date = it.date,
                location = it.location,
                reportId = it.reportId,
                tag = it.tag,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title
            )
        }
    )
}