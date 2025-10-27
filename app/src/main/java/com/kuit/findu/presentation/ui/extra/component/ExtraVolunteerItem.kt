package com.kuit.findu.presentation.ui.extra.component

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.kuit.findu.R
import com.kuit.findu.domain.model.extra.VolunteerWork
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun ExtraVolunteerItem(
    volunteerWork: VolunteerWork,
    modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, volunteerWork.webLink.toUri())


    Column(modifier = modifier.padding(top=20.dp, start = 20.dp, end = 20.dp).noRippleClickable{
        context.startActivity(intent)
    }) {
        Row (verticalAlignment = Alignment.CenterVertically){
            Text(text = volunteerWork.institution, style = FindUTheme.typography.head2SemiBold20)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "상세보기", style = FindUTheme.typography.body2SemiBold14, color = FindUTheme.colors.gray4)
            Spacer(modifier = Modifier.width(2.dp))
            BaseVectorIcon(
                vectorResource = R.drawable.ic_right_arrow_gray_20
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            BaseVectorIcon(vectorResource = R.drawable.ic_home_extra_volunteer_calendar_20)
            Spacer(modifier = Modifier.width(5.dp))
            Text("모집 기간", style = FindUTheme.typography.body2Regular14, color = FindUTheme.colors.gray5)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = volunteerWork.recruitmentPeriod, style = FindUTheme.typography.body2SemiBold14, color = FindUTheme.colors.gray5)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            BaseVectorIcon(vectorResource = R.drawable.ic_home_extra_volunteer_gps_20)
            Spacer(modifier = Modifier.width(5.dp))
            Text("장소", style = FindUTheme.typography.body2Regular14, color = FindUTheme.colors.gray5)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = volunteerWork.address, style = FindUTheme.typography.body2SemiBold14, color = FindUTheme.colors.gray5)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            BaseVectorIcon(vectorResource = R.drawable.ic_home_extra_volunteer_time_calendar_20)
            Spacer(modifier = Modifier.width(5.dp))
            Text("봉사 날짜", style = FindUTheme.typography.body2Regular14, color = FindUTheme.colors.gray5)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = volunteerWork.workPeriod, style = FindUTheme.typography.body2SemiBold14, color = FindUTheme.colors.gray5)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            BaseVectorIcon(vectorResource = R.drawable.ic_home_extra_volunteer_time_20)
            Spacer(modifier = Modifier.width(5.dp))
            Text("봉사 시간", style = FindUTheme.typography.body2Regular14, color = FindUTheme.colors.gray5)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = volunteerWork.workTime, style = FindUTheme.typography.body2SemiBold14, color = FindUTheme.colors.gray5)
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
private fun ExtraVolunteerItemPreview() {
    ExtraVolunteerItem(
        volunteerWork = VolunteerWork(
            institution = "양평군유기동물보호센터",
            recruitmentPeriod = "2025.04.21 ~ 2025.05.20",
            address = "경기도 양평군 어디리",
            workPeriod = "2025.04.21 ~ 2025.05.20",
            workTime = "09:00 ~ 10:00",
            webLink = "www.link.link"
        )
    )
}