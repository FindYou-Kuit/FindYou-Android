package com.example.findu.presentation.ui.common

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.findu.ui.theme.FindUTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = FindUTheme.colors.mainColor,
        strokeWidth = 6.dp,
        backgroundColor = FindUTheme.colors.mainColor2,
        strokeCap = StrokeCap.Square
    )
}

@Composable
fun LoadingIndicatorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = { },
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        LoadingIndicator(
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}