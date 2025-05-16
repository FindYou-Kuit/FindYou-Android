package com.example.findu.presentation.ui.onboarding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findu.R
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.ui.theme.FindUTheme

@Composable
fun OnboardingNickname(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        BaseVectorIcon(vectorResource = R.drawable.ic_onboarding_page_first)
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "닉네임을",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp,
            letterSpacing = (-0.3).sp,
            color = FindUTheme.colors.gray6
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "정해주세요!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp,
            letterSpacing = (-0.3).sp,
            color = FindUTheme.colors.gray6
        )
    }
}

@Preview
@Composable
private fun OnboardingNicknamePreview() {
    OnboardingNickname()
}