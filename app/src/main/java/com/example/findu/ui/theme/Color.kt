package com.example.findu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Main Color
val MainColor = Color(0xFFFFA938)
val MainColor2 = Color(0xFFFFEED9)

// Red
val Red1 = Color(0xFFEF4346)
val Red2 = Color(0xFFFFE7E8)

// Blue
val Blue1 = Color(0xFF1B64DA)
val Blue2 = Color(0xFFDEEBFD)

// Green
val Green1 = Color(0xFF43C661)
val Green2 = Color(0xFFDEFDE4)

// Gray
val Gray1 = Color(0xFFF3F3F3)
val Gray2 = Color(0xFFE7E7E7)
val Gray3 = Color(0xFFCFCFCF)
val Gray4 = Color(0xFFA0A0A0)
val Gray5 = Color(0xFF585858)
val Gray6 = Color(0xFF111111)

// Black & White
val FindUBlack = Color(0xFF000000)
val FindUWhite = Color(0xFFFFFFFF)

@Immutable
data class FindUColors(
    val mainColor: Color,
    val mainColor2: Color,
    val red1: Color,
    val red2: Color,
    val blue1: Color,
    val blue2: Color,
    val green1: Color,
    val green2: Color,
    val gray1: Color,
    val gray2: Color,
    val gray3: Color,
    val gray4: Color,
    val gray5: Color,
    val gray6: Color,
    val black: Color,
    val white: Color,
)

val defaultFindUColors = FindUColors(
    mainColor = MainColor,
    mainColor2 = MainColor2,
    red1 = Red1,
    red2 = Red2,
    blue1 = Blue1,
    blue2 = Blue2,
    green1 = Green1,
    green2 = Green2,
    gray1 = Gray1,
    gray2 = Gray2,
    gray3 = Gray3,
    gray4 = Gray4,
    gray5 = Gray5,
    gray6 = Gray6,
    black = FindUBlack,
    white = FindUWhite,
)

val LocalFindUColors = staticCompositionLocalOf { defaultFindUColors }