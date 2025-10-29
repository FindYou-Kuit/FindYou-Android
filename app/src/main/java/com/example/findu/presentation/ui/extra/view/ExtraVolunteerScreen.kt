package com.example.findu.presentation.ui.extra.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.findu.R
import com.example.findu.domain.model.extra.VolunteerWork
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.extra.component.ExtraVolunteerItem

@Composable
fun ExtraHomeVolunteerScreen(
    volunteerWorks: List<VolunteerWork>,
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = {}
) {
    Column(modifier = modifier) {
        FindUTopAppBar(
            title = R.string.home_extra_volunteer,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = popBackStack
        )
        LazyColumn {
            items(volunteerWorks) {
                ExtraVolunteerItem(volunteerWork = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExtraHomeVolunteerScreenPreview() {
    val dummyVolunteerWorks = listOf(
        VolunteerWork(
            institution = "양평군유기동물보호센터",
            recruitmentPeriod = "2025.04.21 ~ 2025.05.20",
            address = "경기도 양평군 어디리",
            workPeriod = "2025.04.21 ~ 2025.05.20",
            workTime = "09:00 ~ 10:00",
            webLink = "https://www.link.link"
        ),
        VolunteerWork(
            institution = "서울시 동물보호소",
            recruitmentPeriod = "2025.05.01 ~ 2025.05.31",
            address = "서울특별시 강남구 어딘가",
            workPeriod = "2025.06.01 ~ 2025.06.30",
            workTime = "13:00 ~ 17:00",
            webLink = "https://volunteer.seoul.go.kr"
        )
    )

    ExtraHomeVolunteerScreen(volunteerWorks = dummyVolunteerWorks)
}