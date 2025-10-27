package com.kuit.findu.presentation.ui.home.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun HomeWebLinkButton(
    buttonText: String,
    webUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .border(width = 1.dp, color = FindUTheme.colors.mainColor, shape = RoundedCornerShape(20.dp))
            .roundedBackgroundWithPadding(
                backgroundColor = FindUTheme.colors.white,
                cornerRadius = 20.dp,
                padding = PaddingValues(vertical = 12.dp)
            )
            .noRippleClickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
                context.startActivity(intent)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BaseVectorIcon(vectorResource = R.drawable.ic_info_navigate)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = buttonText,
            style = FindUTheme.typography.body1SemiBold16,
            color = FindUTheme.colors.mainColor
        )
    }
}

@Preview
@Composable
private fun HomeWebLinkButtonPreview() {
    HomeWebLinkButton(
        modifier = Modifier.fillMaxWidth(),
        buttonText = "동물정보보호시스템 바로가기",
        webUrl = ""
    )
}