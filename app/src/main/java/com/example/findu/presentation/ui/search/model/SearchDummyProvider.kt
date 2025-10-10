package com.example.findu.presentation.ui.search.model

import androidx.annotation.VisibleForTesting
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.BuildConfig

object DummyProvider {

    @VisibleForTesting
    fun getDummyAnimals(): List<SearchAnimal> {
        if (!BuildConfig.DEBUG) return emptyList() // 🚧 release 에선 빈 리스트 반환

        return listOf(
            SearchAnimal(
                cardId = 1,
                thumbnailImageUrl = "https://picsum.photos/200/300",
                title = "말티즈",
                tag = SearchStatus.PROTECTING,
                date = "2024-11-23",
                location = "성신구 내동 628-1",
                interest = true
            ),
            SearchAnimal(
                cardId = 2,
                thumbnailImageUrl = "https://picsum.photos/200/301",
                title = "믹스견",
                tag = SearchStatus.WITNESS,
                date = "2024-11-24",
                location = "성신구 내동 628-1",
                interest = false
            ),
            SearchAnimal(
                cardId = 3,
                thumbnailImageUrl = "https://picsum.photos/200/302",
                title = "치와와",
                tag = SearchStatus.MISSING,
                date = "2024-11-25",
                location = "성신구 내동 628-1",
                interest = false
            )
        )
    }
}