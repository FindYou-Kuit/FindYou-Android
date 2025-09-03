package com.example.findu.presentation.ui.base

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.findu.ui.theme.FindUTheme

@Composable
fun FindUButton(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .background(
                color = if (enabled) {
                    FindUTheme.colors.mainColor
                } else {
                    FindUTheme.colors.gray1
                },
                shape = RoundedCornerShape(30.dp)
            )
            .clip(
                shape = RoundedCornerShape(30.dp)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = textRes),
            style = FindUTheme.typography.head3SemiBold18.copy(
                color = if (enabled) {
                    FindUTheme.colors.white
                } else {
                    FindUTheme.colors.gray4
                }
            )
        )
    }
}