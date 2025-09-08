package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.findu.R
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeReportDialog(
    onDismissRequest: () -> Unit,
    onLostReportButtonClicked: () -> Unit,
    onFindReportButtonClicked: () -> Unit,
    onPhoneClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier.roundedBackgroundWithPadding(
                backgroundColor = FindUTheme.colors.white,
                padding = PaddingValues(top = 40.dp, bottom = 20.dp),
                cornerRadius = 12.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.img_findu_logo),
                    modifier = Modifier.size(28.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "제보하기",
                    style = FindUTheme.typography.head1SemiBold24,
                    color = FindUTheme.colors.gray6
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                Column(
                    modifier = Modifier.noRippleClickable(onFindReportButtonClicked),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BaseVectorIcon(
                        modifier = Modifier
                            .roundedBackgroundWithPadding(
                                backgroundColor = FindUTheme.colors.blue2,
                                padding = PaddingValues(15.dp),
                                cornerRadius = 10.dp
                            ),
                        vectorResource = R.drawable.ic_dialog_home_report_48_blue
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "목격신고", style = FindUTheme.typography.body1SemiBold16, color = FindUTheme.colors.gray6)
                }
                Spacer(modifier = Modifier.width(20.dp))

                Column(
                    modifier = Modifier.noRippleClickable(onLostReportButtonClicked),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BaseVectorIcon(
                        modifier = Modifier
                            .roundedBackgroundWithPadding(
                                backgroundColor = FindUTheme.colors.red2,
                                padding = PaddingValues(15.dp),
                                cornerRadius = 10.dp
                            ),
                        vectorResource = R.drawable.ic_dialog_home_report_48_red
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "실종신고", style = FindUTheme.typography.body1SemiBold16, color = FindUTheme.colors.gray6)
                }

            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                BaseVectorIcon(
                    vectorResource = R.drawable.ic_home_report_dialog_phone_24
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "전화로 신고하기",
                    style = FindUTheme.typography.tag1SemiBold12.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = FindUTheme.colors.gray5,
                    modifier = Modifier.noRippleClickable(onPhoneClicked)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeReportDialogPreview() {
    FindUTheme {
        HomeReportDialog(
            onDismissRequest = {},
            onLostReportButtonClicked = {},
            onFindReportButtonClicked = {},
            onPhoneClicked = {},
        )
    }
}