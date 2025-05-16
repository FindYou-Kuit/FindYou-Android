package com.example.findu.presentation.ui.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findu.R
import com.example.findu.presentation.type.DefaultProfileType
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun OnboardingProfile(
    defaultProfileClicked: (defaultProfileType: DefaultProfileType) -> Unit,
    modifier: Modifier = Modifier,
    defaultProfileType: DefaultProfileType = DefaultProfileType.NONE
) {
    Column(modifier = modifier.fillMaxWidth()) {
        BaseVectorIcon(vectorResource = R.drawable.ic_onboarding_page_first)
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "프로필을",
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
        Spacer(modifier = Modifier.height(35.dp))
        OnboardingProfileBox(
            defaultProfileType = defaultProfileType,
            cameraClicked = {},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(R.drawable.img_onboarding_default_profile_none_68),
                contentDescription = null,
                Modifier.size(68.dp).noRippleClickable { defaultProfileClicked(DefaultProfileType.NONE) }
            )
            Image(
                painter = painterResource(R.drawable.img_onboarding_default_profile_dog_68),
                contentDescription = null,
                Modifier.size(68.dp).noRippleClickable { defaultProfileClicked(DefaultProfileType.DOG) }
            )
            Image(
                painter = painterResource(R.drawable.img_onboarding_default_profile_chick_68),
                contentDescription = null,
                Modifier.size(68.dp).noRippleClickable { defaultProfileClicked(DefaultProfileType.CHICK) }
            )
            Image(
                painter = painterResource(R.drawable.img_onboarding_default_profile_panda_68),
                contentDescription = null,
                Modifier.size(68.dp).noRippleClickable { defaultProfileClicked(DefaultProfileType.PANDA) }
            )
        }

    }

}

@Preview
@Composable
private fun OnboardingProfilePreview() {
    OnboardingProfile(
        defaultProfileClicked = {}
    )
}