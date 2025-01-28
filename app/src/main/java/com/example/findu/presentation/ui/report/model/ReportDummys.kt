package com.example.findu.presentation.ui.report.model

import androidx.core.net.toUri

object ReportDummys {

    // 더미 uri
    val dummyImageUrls = listOf(
        "https://media.istockphoto.com/id/1853686056/ko/%EC%82%AC%EC%A7%84/%EC%A7%91%EC%97%90%EC%84%9C-%ED%9C%B4%EC%8B%9D%EC%9D%84-%EC%B7%A8%ED%95%98%EB%8A%94-%EA%B3%A8%EB%93%A0-%EB%A6%AC%ED%8A%B8%EB%A6%AC%EB%B2%84.jpg?s=2048x2048&w=is&k=20&c=vzzV2dTo0oeOfBemKyragQfg-qC3sm6Dn0oAZOf6evM=",
        "https://cdn.pixabay.com/photo/2023/09/19/12/34/dog-8262506_640.jpg",
        "https://cdn.pixabay.com/photo/2023/01/02/04/13/dog-7691238_640.jpg",
        "https://cdn.pixabay.com/photo/2020/06/30/22/34/dog-5357794_640.jpg"
    )

    val dummyImageUris = dummyImageUrls.map {
        it.toUri()
    }

    val dummyBreeds = listOf(
        "말티즈",
        "푸들",
        "시츄",
        "포메라니안",
        "요크셔테리어",
        "비숑프리제",
        "치와와",
        "닥스훈트",
        "믹스견",
        "기타",
        "리트리버",
        "보더콜리"
    )
}
