package com.example.findu.presentation.ui.search.model

import java.time.LocalDate

object DummySearchRvData {
    private fun today() = LocalDate.now().toString()

    fun all(): List<SearchRv> = listOf(
        protecting(1001, "쿠키", "서울 강남구"),
        witness(2001, "몽실이", "서울 송파구"),
        missing(3001, "호두", "서울 관악구")
    )

    fun report(): List<SearchRv> = listOf(
        witness(2101, "레오", "서울 광진구"),
        missing(3101, "라떼", "서울 동작구")
    )

    fun rescue(): List<SearchRv> = listOf(
        protecting(1101, "해피", "서울 성북구"),
        protecting(1102, "까미", "서울 중랑구")
    )

    private fun protecting(id: Long, name: String, addr: String) = SearchRv(
        image ="",
        name = name,
        date = today(),
        address = addr,
        isBookmark = false,
        tag = SearchRvTag.PROTECTING,
        cardId = id
    )

    private fun witness(id: Long, name: String, addr: String) = SearchRv(
        image ="",
        name = name,
        date = today(),
        address = addr,
        isBookmark = false,
        tag = SearchRvTag.WITNESS,
        cardId = id
    )

    private fun missing(id: Long, name: String, addr: String) = SearchRv(
        image ="",
        name = name,
        date = today(),
        address = addr,
        isBookmark = false,
        tag = SearchRvTag.MISSING,
        cardId = id
    )
}