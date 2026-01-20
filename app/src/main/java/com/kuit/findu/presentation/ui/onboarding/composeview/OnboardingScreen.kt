package com.kuit.findu.presentation.ui.onboarding.composeview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.presentation.type.DefaultProfileType
import com.kuit.findu.presentation.type.NicknameValidType
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.ui.onboarding.component.OnboardingButton
import com.kuit.findu.presentation.ui.onboarding.component.OnboardingNickname
import com.kuit.findu.presentation.ui.onboarding.component.OnboardingProfile
import com.kuit.findu.presentation.ui.onboarding.viewmodel.OnboardingUiState
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun OnboardingScreen(
    uiState: OnboardingUiState,
    cameraIconClicked: () -> Unit,
    clearProfileImage: () -> Unit,
    backButtonClicked: () -> Unit,
    nextButtonClicked: () -> Unit,
    focusChanged: (Boolean) -> Unit,
    nicknameValueChanged: (String) -> Unit,
    nicknameDuplicateCheck: () -> Unit,
    defaultProfileClicked: (defaultProfileType: DefaultProfileType) -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusManager = LocalFocusManager.current

    val isNextButtonEnabled by remember(uiState.pageState, uiState.nickNameValidState) {
        derivedStateOf {
            uiState.pageState == 1 ||
                    (uiState.pageState == 2 && uiState.nickNameValidState == NicknameValidType.VALID)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = FindUTheme.colors.white)
            .noRippleClickable {
                focusManager.clearFocus()
            }
    ) {
//        BaseVectorIcon(
//            vectorResource = R.drawable.ic_arrow_back_24,
//            modifier = Modifier
//                .padding(horizontal = 20.dp, vertical = 18.dp)
//                .noRippleClickable(backButtonClicked)
//        )
        Spacer(modifier = Modifier.height(110.dp))
        Spacer(modifier = Modifier.height(12.dp))
        /*when (uiState.pageState) {
            1 -> OnboardingProfile(
                modifier = Modifier.padding(horizontal = 20.dp),
                defaultProfileType = uiState.defaultProfileType,
                defaultProfileClicked = defaultProfileClicked,
                cameraIconClicked = cameraIconClicked,
                profileImgUri = uiState.profileImageUri,
                clearProfileImage = clearProfileImage
            )

            2 -> */OnboardingNickname(
                modifier = Modifier.padding(horizontal = 20.dp),
                nicknameValueChanged = { nickname ->
                    nicknameValueChanged(nickname)
                },
                nickname = uiState.nickname,
                nicknameValidState = uiState.nickNameValidState,
                nicknameDuplicateCheck = {
                    focusManager.clearFocus()
                    nicknameDuplicateCheck()
                },
                focusChanged = { focusChanged(it) }
            )
//        }
        Spacer(modifier = Modifier.weight(1f))
        OnboardingButton(
            enabled = isNextButtonEnabled,
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
        nicknameValueChanged = {},
        nicknameDuplicateCheck = {},
        focusChanged = {},
        cameraIconClicked = {},
        clearProfileImage = {},
    )
}