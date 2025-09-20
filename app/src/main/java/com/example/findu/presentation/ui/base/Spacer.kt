package com.example.findu.presentation.ui.base

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(
    height: Dp,
) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun HorizontalSpacer(
    width: Dp,
) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun ColumnScope.VerticalSpacer(
    weight: Float,
) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun RowScope.HorizontalSpacer(
    weight: Float,
) {
    Spacer(modifier = Modifier.weight(weight))
}