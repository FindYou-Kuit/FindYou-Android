package com.example.findu.presentation.ui.report.component

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.RoundedInputField
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ReportInputComponent(
    modifier: Modifier = Modifier,
    isEssential: Boolean = false,
    @StringRes titleRes: Int,
    @StringRes placeHolderRes: Int,
    state: TextFieldState,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(titleRes),
                style = FindUTheme.typography.body1SemiBold16,
            )
            if(isEssential) {
                Text(
                    text = stringResource(R.string.asterisk),
                    style = FindUTheme.typography.body2SemiBold14.copy(color = FindUTheme.colors.red1)
                )
            }
        }
        RoundedInputField(
            modifier = modifier,
            state = state,
            placeHolderRes = placeHolderRes,
            singleLine = true,
            interactionSource = interactionSource,
            onKeyboardAction = onKeyboardAction,
        )
    }
}