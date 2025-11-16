package com.kuit.findu.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.kuit.findu.R

enum class NicknameValidType(
    @StringRes val stringRes: Int,
    @ColorRes val colorRes: Int
) {
    IDLE(
        stringRes = R.string.nickname_validate_text_idle,
        colorRes = R.color.gray2
    ),
    FOCUS(
        stringRes = R.string.nickname_validate_text_focus,
        colorRes = R.color.blue1
    ),
    VALID(
        stringRes = R.string.nickname_validate_text_valid,
        colorRes = R.color.green1
    ),
    EMPTY_INVALID(
        stringRes = R.string.nickname_validate_text_empty,
        colorRes = R.color.red1
    ),
    FORMAT_INVALID(
        stringRes = R.string.nickname_validate_text_format,
        colorRes = R.color.red1
    ),
    DUPLICATE_INVALID(
        stringRes = R.string.nickname_validate_text_duplicate,
        colorRes = R.color.red1
    );
}