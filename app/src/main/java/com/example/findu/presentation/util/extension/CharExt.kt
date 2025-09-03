package com.example.findu.presentation.util.extension

import kotlinx.datetime.*
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