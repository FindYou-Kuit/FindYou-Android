package com.kuit.findu.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kuit.findu.R
import com.kuit.findu.presentation.ui.base.RectangleFindUButton
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun AppSettingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    openAppSettings: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .background(
                    color = FindUTheme.colors.gray1,
                    shape = RoundedCornerShape(15.dp),
                )
                .padding(horizontal = 20.dp)
                .padding(top = 40.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = stringResource(R.string.permission_dialog_title),
                style = FindUTheme.typography.head3SemiBold18.copy(),
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.permission_dialog_body),
                style = FindUTheme.typography.body1Regular16.copy(),
                textAlign = TextAlign.Center,
            )
            RectangleFindUButton(
                textRes = R.string.my_set_camera_permission,
                onClick = {
                    openAppSettings()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
private fun AppSettingDialogPreview() {
    FindUTheme {
        AppSettingDialog(
            onDismissRequest = {},
            openAppSettings = {}
        )
    }
}