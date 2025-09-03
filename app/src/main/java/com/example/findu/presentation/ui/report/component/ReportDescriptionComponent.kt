package com.example.findu.presentation.ui.report.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.HorizontalSpacer
import com.example.findu.presentation.ui.base.RoundedInputField
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ReportDescriptionComponent(
    modifier: Modifier = Modifier,
    descriptionState: TextFieldState,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = stringResource(R.string.report_description_title),
                style = FindUTheme.typography.body2SemiBold14,
            )
            HorizontalSpacer( 2.dp)
            Text(
                text = stringResource(R.string.report_description_title_additional),
                style = FindUTheme.typography.captionRegular12.copy(
                    color = FindUTheme.colors.gray3
                ),
            )
        }
        RoundedInputField(
            modifier = modifier.heightIn(min = 98.dp),
            state = descriptionState,
            placeHolderRes = R.string.report_description_placeholder,
            singleLine = false,
            interactionSource = interactionSource,
            onKeyboardAction = onKeyboardAction,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportDescriptionComponentPreview() {
    FindUTheme {
        ReportDescriptionComponent(
            descriptionState = TextFieldState(),
        )
    }
}