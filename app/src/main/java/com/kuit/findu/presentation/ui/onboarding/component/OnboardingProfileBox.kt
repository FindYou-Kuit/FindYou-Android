package com.kuit.findu.presentation.ui.onboarding.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kuit.findu.R
import com.kuit.findu.presentation.type.DefaultProfileType
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun OnboardingProfileBox(
    cameraClicked:()->Unit,
    modifier: Modifier = Modifier,
    defaultProfileType: DefaultProfileType = DefaultProfileType.DEFAULT,
    profileImgUri: Uri?,
) {
    Box(modifier = modifier.size(145.dp)) {
        if (profileImgUri==null) {
            val drawableRes = when (defaultProfileType) {
                DefaultProfileType.DEFAULT -> R.drawable.img_onboarding_default_profile_none_68
                DefaultProfileType.PUPPY -> R.drawable.img_onboarding_default_profile_dog_68
                DefaultProfileType.CHICK -> R.drawable.img_onboarding_default_profile_chick_68
                DefaultProfileType.PANDA -> R.drawable.img_onboarding_default_profile_panda_68
            }
            Image(painter = painterResource(drawableRes), contentDescription = null,modifier = Modifier.fillMaxSize())
        } else {
            AsyncImage(
                model = profileImgUri,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.img_onboarding_default_profile_none_68),
                error = painterResource(R.drawable.img_onboarding_default_profile_none_68)
            )
        }

        BaseVectorIcon(
            vectorResource = R.drawable.ic_profile_camera_44,
            modifier = Modifier
                .noRippleClickable { cameraClicked() }
                .align(Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
private fun OnboardingProfileBoxPreview() {
    FindUTheme { OnboardingProfileBox(
        cameraClicked = {},
        profileImgUri = null
    ) }
}