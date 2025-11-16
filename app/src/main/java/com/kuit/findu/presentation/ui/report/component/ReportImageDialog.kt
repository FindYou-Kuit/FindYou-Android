package com.kuit.findu.presentation.ui.report.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kuit.findu.R
import com.kuit.findu.presentation.ui.base.VerticalSpacer
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun ReportImageDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onCameraClick: () -> Unit = {},
    onGalleryClick: () -> Unit = {},
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
                .padding(top = 40.dp, bottom = 20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_report_upload_20),
                    contentDescription = null,
                    tint = FindUTheme.colors.mainColor
                )
                Text(
                    text = stringResource(R.string.report_dialog_good_image),
                    style = FindUTheme.typography.head3SemiBold18.copy(
                        color = FindUTheme.colors.mainColor
                    )
                )
            }
            VerticalSpacer(5.dp)
            Text(
                text = stringResource(R.string.report_dialog_ai_tip),
                style = FindUTheme.typography.body2SemiBold14.copy(
                    color = FindUTheme.colors.gray4
                )
            )
            VerticalSpacer(25.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = FindUTheme.colors.mainColor2,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .noRippleClickable(onClick = {
                            onCameraClick()
                            onDismissRequest()
                        })
                        .padding(top = 35.dp, bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_report_dialog_camera),
                        contentDescription = null,
                        modifier = Modifier.size(42.dp, 30.dp)
                    )
                    Text(
                        text = stringResource(R.string.report_dialog_capture),
                        style = FindUTheme.typography.body2SemiBold14.copy(
                            color = FindUTheme.colors.gray6
                        )
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = FindUTheme.colors.mainColor2,
                            shape = RoundedCornerShape(8.dp),
                        )
                        .noRippleClickable(onClick = {
                            onGalleryClick()
                            onDismissRequest()
                        })
                        .padding(top = 35.dp, bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_report_dialog_gallery),
                        contentDescription = null,
                        modifier = Modifier.size(42.dp, 30.dp)
                    )
                    Text(
                        text = stringResource(R.string.report_dialog_upload),
                        style = FindUTheme.typography.body2SemiBold14.copy(
                            color = FindUTheme.colors.gray6
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportImageDialogPreview() {
    FindUTheme {
        ReportImageDialog()
    }
}