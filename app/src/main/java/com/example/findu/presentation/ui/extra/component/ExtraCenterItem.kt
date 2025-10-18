package com.example.findu.presentation.ui.extra.component

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.findu.R
import com.example.findu.domain.model.extra.Center
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ExtraCenterItem(
    center: Center,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_DIAL, "tel:${center.phoneNumber}".toUri())
    val clipboardManager = LocalClipboardManager.current


    Column(modifier = modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {

        Text(
            text = center.centerName,
            style = FindUTheme.typography.head2SemiBold20,
            color = FindUTheme.colors.gray6
        )
        Spacer(modifier = Modifier.height(5.dp))
        center.jurisdiction.forEach {jurisdiction->
            Text(
                text = jurisdiction,
                style = FindUTheme.typography.body1SemiBold16,
                color = FindUTheme.colors.gray5
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Tel) ${center.phoneNumber} >", modifier = Modifier.noRippleClickable {
                context.startActivity(intent)
            }, style = FindUTheme.typography.body1Regular16,
            color = FindUTheme.colors.blue1
        )
        Spacer(modifier = Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = center.centerName,
                style = FindUTheme.typography.captionRegular12,
                color = FindUTheme.colors.gray6
            )
            Spacer(modifier = Modifier.width(10.dp))
            BaseVectorIcon(
                vectorResource = R.drawable.ic_home_extra_copy_15,
                modifier = Modifier.noRippleClickable {
                    clipboardManager.setText(AnnotatedString(center.address))
                })
            Text(
                text = stringResource(R.string.home_extra_copy),
                style = FindUTheme.typography.body2Regular14,
                color = Color(0xFF00D1CA),
                modifier = Modifier.noRippleClickable {
                    clipboardManager.setText(AnnotatedString(center.address))
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = FindUTheme.colors.gray2,
            thickness = 1.dp,
        )
    }
}

@Preview
@Composable
private fun ExtraCenterItemPreview() {
    ExtraCenterItem(
        center = Center(
            jurisdiction = listOf("서울특별시 강남구", "서울특별시 서초구"),
            centerName = "한국동물구조관리협회",
            phoneNumber = "02-764-3708",
            address = "서울특별시 강남구 삼성로 1 삼성빌딩 1층",
            latitude = 37.497942,
            longitude = 127.027683
        )
    )
}