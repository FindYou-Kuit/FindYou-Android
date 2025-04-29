package com.example.findu.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.findu.R

// Pretendard 폰트 설정
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold))

@Immutable
data class FindUTypography(
    // Head
    val head1SemiBold24: TextStyle,
    val head2SemiBold20: TextStyle,
    val head3SemiBold18: TextStyle,
    val head3Regular18: TextStyle,

    // Body1
    val body1SemiBold16: TextStyle,
    val body1Regular16: TextStyle,

    // Body2
    val body2SemiBold14: TextStyle,
    val body2Regular14: TextStyle,

    // Tag
    val tag1SemiBold12: TextStyle,

    // Caption
    val captionRegular12: TextStyle,
    val captionRegular11: TextStyle,

    // Home
    val homeSemiBold10: TextStyle,

    // My
    val myRegular24: TextStyle
)

val defaultFindUTypography = FindUTypography(
    // Head
    head1SemiBold24 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.2
    ),
    head2SemiBold20 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2
    ),
    head3SemiBold18 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.2
    ),
    head3Regular18 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.2
    ),

    // Body1
    body1SemiBold16 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.2
    ),
    body1Regular16 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.2
    ),

    // Body2
    body2SemiBold14 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.2
    ),
    body2Regular14 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.2
    ),

    // Tag
    tag1SemiBold12 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.2
    ),

    // Caption
    captionRegular12 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.2
    ),
    captionRegular11 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 11.sp,
        lineHeight = 11.sp * 1.2
    ),

    // Home
    homeSemiBold10 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 10.sp,
        lineHeight = 10.sp * 1.2
    ),

    // My
    myRegular24 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.2
    )
)

val LocalFindUTypography = staticCompositionLocalOf { defaultFindUTypography }