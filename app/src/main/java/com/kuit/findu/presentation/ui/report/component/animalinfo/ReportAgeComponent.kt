package com.kuit.findu.presentation.ui.report.component.animalinfo

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.presentation.ui.base.HorizontalSpacer
import com.kuit.findu.presentation.util.extension.isNotDigit
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun ReportAgeComponent(
    modifier: Modifier = Modifier,
    age: TextFieldState,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.report_age_introduction),
                style = FindUTheme.typography.body1SemiBold16,
            )
            Text(
                text = stringResource(R.string.asterisk),
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.red1
            )
        }
        Row {
            Column(
                modifier = Modifier.width(30.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.End
            ) {
                BasicTextField(
                    state = age,
                    textStyle = FindUTheme.typography.body2SemiBold14.copy(
                        color =
                            if (isFocused) FindUTheme.colors.mainColor
                            else FindUTheme.colors.gray6
                    ),
                    interactionSource = interactionSource,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    inputTransformation = {
                        val sequence = asCharSequence()
                        if (sequence.length > 2 || sequence.any { it.isNotDigit() }) {
                            revertAllChanges()
                        }
                    },
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color =
                                if (isFocused) FindUTheme.colors.mainColor
                                else FindUTheme.colors.gray6,
                        )
                )
            }
            HorizontalSpacer(7.dp)
            Text(
                text = stringResource(R.string.report_age_postfix),
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.gray6,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportAgeComponentPreview() {
    FindUTheme {
        ReportAgeComponent(
            modifier = Modifier.padding(20.dp),
            age = remember { TextFieldState("") },
        )
    }
}