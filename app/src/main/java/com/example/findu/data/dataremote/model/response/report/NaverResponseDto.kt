package com.example.findu.data.dataremote.model.response.report


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NaverResponseDto(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("status")
    val status: Status
) {
    @Serializable
    data class Result(
        @SerialName("code")
        val code: Code,
        @SerialName("name")
        val name: String,
        @SerialName("region")
        val region: Region
    ) {
        @Serializable
        data class Code(
            @SerialName("id")
            val id: String,
            @SerialName("mappingId")
            val mappingId: String,
            @SerialName("type")
            val type: String
        )

        @Serializable
        data class Region(
            @SerialName("area0")
            val area0: Area,
            @SerialName("area1")
            val area1: Area,
            @SerialName("area2")
            val area2: Area,
            @SerialName("area3")
            val area3: Area,
            @SerialName("area4")
            val area4: Area
        ) {
            @Serializable
            data class Area(
                @SerialName("coords")
                val coords: Coords,
                @SerialName("name")
                val name: String
            ) {
                @Serializable
                data class Coords(
                    @SerialName("center")
                    val center: Center
                ) {
                    @Serializable
                    data class Center(
                        @SerialName("crs")
                        val crs: String,
                        @SerialName("x")
                        val x: Double,
                        @SerialName("y")
                        val y: Double
                    )
                }
            }
        }
    }

    @Serializable
    data class Status(
        @SerialName("code")
        val code: Int,
        @SerialName("message")
        val message: String,
        @SerialName("name")
        val name: String
    )
}