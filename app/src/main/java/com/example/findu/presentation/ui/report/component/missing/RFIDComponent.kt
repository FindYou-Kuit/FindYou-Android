package com.example.findu.presentation.ui.report.component.missing

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.RoundedInputField
import com.example.findu.ui.theme.FindUTheme

@Composable
fun RFIDComponent(
    modifier: Modifier = Modifier,
    rfidState: TextFieldState,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(R.string.report_rfid_title),
            style = FindUTheme.typography.body1SemiBold16,
        )
        RoundedInputField(
            modifier = modifier,
            state = rfidState,
            placeHolderRes = R.string.report_rfid_placeholder,
            singleLine = true,
            interactionSource = interactionSource,
            onKeyboardAction = onKeyboardAction,
        )
    }
}

@Preview
@Composable
private fun RFIDComponentPreview() {
    FindUTheme {
        RFIDComponent(
            rfidState = TextFieldState(),
        )
    }
}