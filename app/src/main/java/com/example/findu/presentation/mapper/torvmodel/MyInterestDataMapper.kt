package com.example.findu.presentation.mapper.torvmodel

import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.presentation.model.MyInterestRv

fun MyInterestData.InterestAnimal.toRvModel(): MyInterestRv {
    return MyInterestRv(
        interestId = this.interestId,
        animalId = this.animalId,
        isProtectingAnimal = this.isProtectingAnimal,
        thumbnailImageUrl = this.thumbnailImageUrl,
        title = this.title,
        tag = this.tag,
        date = this.date,
        location = this.location,
        interest = this.interest
    )
}
