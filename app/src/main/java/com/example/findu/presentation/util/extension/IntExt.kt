package com.example.findu.presentation.util.extension

import java.text.NumberFormat
import java.util.Locale


fun Int.toStringWithComma(): String = NumberFormat.getNumberInstance(Locale.KOREA).format(this)