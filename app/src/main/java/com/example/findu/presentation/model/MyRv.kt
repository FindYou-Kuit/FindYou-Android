package com.example.findu.presentation.model

data class MyReportHistoryRv(
    val reportId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val location: String
) {
    companion object {
        val dummyItems = listOf(
            MyReportHistoryRv(
                1,
                "https://cdn.pixabay.com/photo/2024/12/27/14/58/owl-9294302_640.jpg",
                "말티즈",
                "보호중",
                "2021.09.01",
                "서울시 강남구"
            ),
            MyReportHistoryRv(
                1,
                "https://cdn.pixabay.com/photo/2024/12/27/14/58/owl-9294302_640.jpg",
                "말티즈",
                "보호중",
                "2021.09.01",
                "서울시 강남구"
            )
        )
    }
}

data class MyRecentHistoryRv(
    val cardId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val location: String,
    var interest: Boolean,
) {
    companion object {
        val dummyItems = listOf(
            MyRecentHistoryRv(
                1,
                "https://cdn.pixabay.com/photo/2024/12/27/14/58/owl-9294302_640.jpg",
                "말티즈",
                "목격신고",
                "2021.09.01",
                "서울시 강남구",
                false
            ),
            MyRecentHistoryRv(
                1,
                "https://cdn.pixabay.com/photo/2024/12/27/14/58/owl-9294302_640.jpg",
                "말티즈",
                "실종신고",
                "2021.09.01",
                "서울시 강남구",
                false
            )
        )
    }
}

data class MyInterestRv(
    val animalId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val location: String,
    var interest: Boolean,
) {
    companion object {
        val dummyItems = listOf(
            MyInterestRv(
                1,
                "https://cdn.pixabay.com/photo/2024/12/27/14/58/owl-9294302_640.jpg",
                "말티즈",
                "보호중",
                "2021.09.01",
                "서울시 강남구",
                false
            ),
            MyInterestRv(
                1,
                "https://cdn.pixabay.com/photo/2024/12/27/14/58/owl-9294302_640.jpg",
                "말티즈",
                "보호중",
                "2021.09.01",
                "서울시 강남구",
                false
            )
        )
    }
}