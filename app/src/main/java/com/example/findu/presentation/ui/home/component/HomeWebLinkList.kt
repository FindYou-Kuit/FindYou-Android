package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.WebViewUrl.CENTER_URL
import com.example.findu.presentation.util.WebViewUrl.PART_URL
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeWebLinkList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = FindUTheme.colors.mainColor2)
            .padding(vertical = 30.dp, horizontal = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "유기동물 관련 사이트", style = FindUTheme.typography.head2SemiBold20, color = FindUTheme.colors.gray6)
            Spacer(modifier = Modifier.width(5.dp))
            BaseVectorIcon(vectorResource = R.drawable.ic_home_info_title_24)
        }
        Spacer(modifier = Modifier.height(30.dp))
        HomeWebLinkButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = "동물정보보호시스템 바로가기",
            webUrl = PART_URL
        )
        Spacer(modifier = Modifier.height(10.dp))
        HomeWebLinkButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = "동물자유연대 바로가기",
            webUrl = CENTER_URL
        )
        Spacer(modifier = Modifier.height(10.dp))
        HomeWebLinkButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = "종합유기견보호센터 바로가기",
            webUrl = CENTER_URL
        )
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "서비스 관련 정보 \n" +
                    "문의처는 어쩌고 저쩌고 \n" +
                    "전화번호 00-10-3-20442",
            style = FindUTheme.typography.captionRegular12,
            color = FindUTheme.colors.gray5
        )

    }
}

@Preview
@Composable
private fun HomeWebLinkListPreview() {
    HomeWebLinkList()
}