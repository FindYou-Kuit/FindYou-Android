package com.example.findu.presentation.ui.base

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.findu.ui.theme.FindUTheme

@Composable
fun RoundedInputField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    @StringRes placeHolderRes: Int,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    val isFocused = interactionSource?.collectIsFocusedAsState()?.value

    BasicTextField(
        state = state,
        enabled = enabled,
        textStyle = FindUTheme.typography.tag1SemiBold12,
        onKeyboardAction = onKeyboardAction,
        interactionSource = interactionSource,
        lineLimits = if (singleLine) TextFieldLineLimits.SingleLine else TextFieldLineLimits.MultiLine(),
        decorator = { innerTextField ->

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isFocused == true) FindUTheme.colors.mainColor
                        else FindUTheme.colors.gray3,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                if (state.text.isEmpty()) {
                    Text(
                        text = stringResource(placeHolderRes),
                        style = FindUTheme.typography.tag1SemiBold12,
                        color = FindUTheme.colors.gray4,
                    )
                } else {
                    innerTextField()
                }
            }
        },
    )
}