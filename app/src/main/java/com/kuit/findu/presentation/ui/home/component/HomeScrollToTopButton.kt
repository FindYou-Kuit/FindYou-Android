package com.kuit.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun HomeScrollToTopButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(shape = CircleShape, color = FindUTheme.colors.white)
            .border(
                width = 1.dp,
                color = FindUTheme.colors.gray3,
                shape = CircleShape
            )
            .padding(11.dp)
            .noRippleClickable(onClick), contentAlignment = Alignment.Center
    ) {
        BaseVectorIcon(vectorResource = R.drawable.ic_arrow_top_white_24, tint = FindUTheme.colors.gray4)
    }
}

@Preview
@Composable
private fun HomeScrollToTopButtonPreview() {
    HomeScrollToTopButton(
        onClick = {}
    )
}