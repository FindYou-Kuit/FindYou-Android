package com.kuit.findu.presentation.ui.login.composeview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun LoginScreen(
    kakaoLoginButtonClicked: () -> Unit,
    withoutSignUpButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = FindUTheme.colors.white)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1.1f))
        Text(
            text = stringResource(R.string.login_title_first),
            style = FindUTheme.typography.head1SemiBold24,
            color = FindUTheme.colors.gray6,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(R.string.login_title_second),
            style = FindUTheme.typography.head1SemiBold24,
            color = FindUTheme.colors.gray6,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.login_description_text),
            style = FindUTheme.typography.body2Regular14,
            color = FindUTheme.colors.gray4,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(34.dp))

        Icon(
            painter = painterResource(R.drawable.img_login_logo),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.height(100.dp))
        Icon(
            painter = painterResource(R.drawable.img_kakao_login),
            contentDescription = null,
            modifier = Modifier.noRippleClickable(onClick = kakaoLoginButtonClicked),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(15.dp))

        TextButton(
            onClick = withoutSignUpButtonClicked,
        ) {
            Text(
                text = stringResource(R.string.login_without_signup),
                textAlign = TextAlign.Center,
                style = FindUTheme.typography.body1SemiBold16,
                color = FindUTheme.colors.gray5,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.weight(1.5f))

    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        kakaoLoginButtonClicked = {},
        withoutSignUpButtonClicked = {},
    )
}