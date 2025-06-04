package com.example.findu.presentation.ui.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findu.R
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.presentation.util.shape.TriangleShape
import com.example.findu.ui.theme.FindUTheme

@Composable
fun OnboardingNicknameTextBox(modifier: Modifier = Modifier, isVisible: Boolean = false) {
    if (isVisible) {
        Column(modifier = modifier) {
            Spacer(
                modifier = Modifier
                    .padding(end = 28.dp)
                    .size(15.dp)
                    .offset(y = 2.dp)
                    .background(shape = TriangleShape(), color = FindUTheme.colors.gray2)
                    .align(Alignment.End)
            )
            Box(
                modifier = Modifier
                    .roundedBackgroundWithPadding(
                        backgroundColor = FindUTheme.colors.gray2,
                        cornerRadius = 8.dp,
                        padding = PaddingValues(15.dp)
                    )
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.onboarding_invalid_text_box),
                    style = FindUTheme.typography.captionRegular12.copy(
                        lineHeight = 20.sp
                    ),
                    color = FindUTheme.colors.gray5
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingNicknameTextBoxPreview() {
    OnboardingNicknameTextBox(isVisible = true)
}