package com.kuit.findu.data.mapper.todomain.extra

import com.kuit.findu.data.dataremote.model.response.information.SidoListDto
import com.kuit.findu.domain.model.extra.Sido

fun SidoListDto.toDomain(): List<Sido> =
    sidoList.map { sidoItemDto ->
        Sido(
            id = sidoItemDto.id,
            name = sidoItemDto.name
        )
    }
