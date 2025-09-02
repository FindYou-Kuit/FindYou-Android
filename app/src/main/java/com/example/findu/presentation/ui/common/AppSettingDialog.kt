package com.example.findu.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.findu.R
import com.example.findu.presentation.ui.base.FindUButton
import com.example.findu.ui.theme.FindUTheme

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
                "접근 권한이 없어 해당 기능을 사용할 수 없습니다.",
                style = FindUTheme.typography.head3SemiBold18.copy(),
                textAlign = TextAlign.Center,
            )
            Text(
                "권한을 허용하시려면 설정을 눌러주세요.\n" +
                        "필요 권한 : 카메라",
                style = FindUTheme.typography.body1Regular16.copy(),
                textAlign = TextAlign.Center,
            )
            FindUButton(
                textRes = R.string.my_set_camera_permission,
                onClick = {
                    openAppSettings()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = FindUTheme.colors.mainColor,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .clip(RoundedCornerShape(8.dp))
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