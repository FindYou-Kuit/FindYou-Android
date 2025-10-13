package com.example.findu.data.mapper.todomain.extra

import com.example.findu.data.dataremote.model.response.information.SidoListDto
import com.example.findu.domain.model.extra.Sido

fun SidoListDto.toDomain(): List<Sido> =
    sidoList.map { sidoItemDto ->
        Sido(
            id = sidoItemDto.id,
            name = sidoItemDto.name
        )
    }
