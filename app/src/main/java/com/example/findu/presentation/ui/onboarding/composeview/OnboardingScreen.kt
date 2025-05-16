package com.example.findu.presentation.ui.onboarding.composeview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.type.DefaultProfileType
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.ui.onboarding.component.OnboardingButton
import com.example.findu.presentation.ui.onboarding.component.OnboardingNickname
import com.example.findu.presentation.ui.onboarding.component.OnboardingProfile
import com.example.findu.presentation.ui.onboarding.viewmodel.OnboardingUiState
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun OnboardingScreen(
    uiState: OnboardingUiState,
    backButtonClicked: () -> Unit,
    nextButtonClicked: () -> Unit,
    defaultProfileClicked: (defaultProfileType: DefaultProfileType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = FindUTheme.colors.white)
    ) {
        BaseVectorIcon(
            vectorResource = R.drawable.ic_arrow_back_24,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 18.dp)
                .noRippleClickable(backButtonClicked)
        )
        Spacer(modifier = Modifier.height(12.dp))
        when (uiState.pageState) {
            1 -> OnboardingProfile(
                modifier = Modifier.padding(horizontal = 20.dp),
                defaultProfileType = uiState.defaultProfileType,
                defaultProfileClicked = defaultProfileClicked
            )
            2 -> OnboardingNickname(
                modifier = Modifier.padding(horizontal = 20.dp),
                )
        }
        Spacer(modifier = Modifier.weight(1f))
        OnboardingButton(
            enabled = uiState.isNextButtonEnabled,
            onClick = nextButtonClicked
        )
        Spacer(modifier = Modifier.height(30.dp))

    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen(
        backButtonClicked = {},
        nextButtonClicked = {},
        uiState = OnboardingUiState(),
        defaultProfileClicked = {},
    )
}