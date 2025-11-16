package com.kuit.findu.presentation.util.extension

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Char.isNotDigit() = this.isDigit().not()


fun LocalDateTime.toKoreanDateString(): String {
    val date = this.date
    val dayOfWeek = when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
    }

    return "%04d년 %02d월 %02d일 (%s)".format(
        date.year, date.monthNumber, date.dayOfMonth, dayOfWeek
    )
}

fun String.toNormalizeAddress(): String {
    val firstWord = this.substringBefore(" ")
    val replaced = firstWord.toCityOrProvince()
    return this.replaceFirst(firstWord, replaced)
}

fun String.toCityOrProvince(): String {
    val map = mapOf(
        "서울" to "서울특별시",
        "부산" to "부산광역시",
        "대구" to "대구광역시",
        "인천" to "인천광역시",
        "광주" to "광주광역시",
        "세종특별자치시" to "세종특별자치시", // 그대로
        "대전" to "대전광역시",
        "울산" to "울산광역시",
        "경기" to "경기도",
        "강원특별자치도" to "강원특별자치도", // 그대로
        "충북" to "충청북도",
        "충남" to "충청남도",
        "전북특별자치도" to "전북특별자치도", // 그대로
        "전남" to "전라남도",
        "경북" to "경상북도",
        "경남" to "경상남도",
        "제주특별자치도" to "제주특별자치도" // 그대로
    )
    return map[this] ?: this
}

/**
 * "2023년 08월 15일" 형식의 날짜를 "2023-08-15" 형식으로 변환
 */
fun String.toDateString(): String =
    if (this.isBlank()) {
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date.toString()
    } else {
        val dateParts = this.replace("년 ", "-")
            .replace("월 ", "-")
            .replace("일", "")
            .split("-")
        val year = dateParts[0].toInt()
        val month = dateParts[1].toInt()
        val day = dateParts[2].toInt()
        LocalDateTime(year, month, day, 0, 0)
            .date.toString()
    }