package com.example.findu.presentation.ui.onboarding.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findu.R
import com.example.findu.presentation.type.NicknameValidType
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@Composable
fun OnboardingNickname(
    nicknameValueChanged: (String) -> Unit,
    nicknameDuplicateCheck:()->Unit,
    modifier: Modifier = Modifier,
    focusChanged:(Boolean)->Unit,
    nickname: String = "",
    nicknameValidState: NicknameValidType = NicknameValidType.IDLE,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        focusChanged(isFocused)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        BaseVectorIcon(vectorResource = R.drawable.ic_onboarding_page_last)
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
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, shape = RoundedCornerShape(30.dp), color = colorResource(nicknameValidState.colorRes))
                .roundedBackgroundWithPadding(
                    cornerRadius = 30.dp,
                    backgroundColor = FindUTheme.colors.white,
                    padding = PaddingValues(horizontal = 23.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = nickname,
                onValueChange = nicknameValueChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp),
                singleLine = true,
                interactionSource = interactionSource,
                cursorBrush = SolidColor(FindUTheme.colors.blue1),
                textStyle = FindUTheme.typography.body2Regular14.copy(color = FindUTheme.colors.gray6),
                decorationBox = { innerTextField ->
                    if (nickname.isEmpty()) {
                        Text(
                            text = "닉네임을 입력해주세요",
                            style = FindUTheme.typography.body2Regular14.copy(color = FindUTheme.colors.gray4)
                        )
                    }
                    innerTextField()
                }
            )
            when (nicknameValidState) {
                NicknameValidType.FOCUS  -> {
                    Text(
                        modifier = Modifier.noRippleClickable(nicknameDuplicateCheck),
                        text = "중복확인",
                        style = FindUTheme.typography.body2Regular14,
                        color = FindUTheme.colors.mainColor
                    )
                }
                NicknameValidType.VALID, NicknameValidType.IDLE  -> {}
                else -> {
                    BaseVectorIcon(
                        vectorResource = R.drawable.ic_nickname_information_24
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(nicknameValidState.stringRes),
            modifier = Modifier.padding(start = 23.dp),
            color = if (nicknameValidState == NicknameValidType.FOCUS) FindUTheme.colors.gray4 else colorResource(nicknameValidState.colorRes),
            style = FindUTheme.typography.captionRegular12
        )
    }
}

@Preview
@Composable
private fun OnboardingNicknamePreview() {
    FindUTheme {
        OnboardingNickname(
            nicknameValueChanged = {},
            nicknameDuplicateCheck = {},
            focusChanged = {},
        )
    }
}