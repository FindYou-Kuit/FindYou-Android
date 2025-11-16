package com.kuit.findu.presentation.ui.onboarding.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun OnboardingButton(
    modifier: Modifier = Modifier, enabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    val (backgroundColor, textColor, buttonClicked) = when (enabled) {
        true -> Triple(
            FindUTheme.colors.mainColor,
            FindUTheme.colors.white,
            onClick
        )

        false -> Triple(
            FindUTheme.colors.gray2,
            FindUTheme.colors.gray4,
            {}
        )
    }

    Text(
        text = stringResource(R.string.onboarding_next_button_text), textAlign = TextAlign.Center,style = FindUTheme.typography.head3SemiBold18, color = textColor, modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .roundedBackgroundWithPadding(
                padding = PaddingValues(vertical = 16.dp),
                backgroundColor = backgroundColor,
                cornerRadius = 30.dp,
            )
            .noRippleClickable(buttonClicked)
    )

}