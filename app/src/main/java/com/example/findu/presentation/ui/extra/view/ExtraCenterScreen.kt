package com.example.findu.presentation.ui.extra.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.extra.Center
import com.example.findu.presentation.type.HomeExtraDistrictType
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.extra.component.ExtraCenterItem
import com.example.findu.presentation.ui.extra.component.ExtraDistrictItem
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ExtraHomeCenterScreen(
    centers: List<Center>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        FindUTopAppBar(
            title = R.string.home_extra_center,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = {},
            modifier = Modifier.background(color = FindUTheme.colors.white)
        )
        Row (modifier = Modifier.padding(20.dp)){
            ExtraDistrictItem(
                districtType = HomeExtraDistrictType.DISTRICT_TYPE_SIDO,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))

            ExtraDistrictItem(
                districtType = HomeExtraDistrictType.DISTRICT_TYPE_SIGUNGU,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .height(340.dp)
                .background(
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    color = FindUTheme.colors.white
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(width = 120.dp, height = 4.dp)
                    .background(color = Color(0xFFE5E5E7), shape = RoundedCornerShape(6.dp)),
                color = FindUTheme.colors.gray2,
                thickness = 1.dp,
            )
            LazyColumn {
                items(centers) {
                    ExtraCenterItem(center = it)
                }
            }
        }

    }
}

@Preview
@Composable
private fun ExtraHomeCenterScreenPreview() {
    val dummyCenters = listOf(
        Center(
            jurisdiction = listOf("서울특별시 강남구", "서울특별시 서초구"),
            centerName = "한국동물구조관리협회",
            phoneNumber = "02-764-3708",
            address = "서울특별시 강남구 삼성로 1 삼성빌딩 1층"
        ),
        Center(
            jurisdiction = listOf("부산광역시 해운대구", "부산광역시 수영구"),
            centerName = "부산 유기동물 구조센터",
            phoneNumber = "051-987-6543",
            address = "부산광역시 해운대구 해운대로 123"
        ),
        Center(
            jurisdiction = listOf("경기도 성남시 분당구"),
            centerName = "경기 동물보호소",
            phoneNumber = "031-555-1111",
            address = "경기도 성남시 분당구 정자동 100"
        )
    )

    ExtraHomeCenterScreen(
        centers = dummyCenters
    )
}