package com.example.findu.presentation.ui.extra.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.extra.Department
import com.example.findu.presentation.type.HomeExtraDistrictType
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.extra.component.ExtraDepartmentItem
import com.example.findu.presentation.ui.extra.component.ExtraDistrictItem

@Composable
fun ExtraHomeDepartmentScreen(
    departments: List<Department>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        FindUTopAppBar(
            title = R.string.home_extra_department,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = {}
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
        LazyColumn {
            items(departments) {
                ExtraDepartmentItem(department = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExtraHomeDepartmentScreenPreview() {
    val dummyDepartments = listOf(
        Department(
            name = "관광체육과 동물보호팀",
            district = "서울특별시 용산구",
            phone = "02-1234-1234"
        ),
        Department(
            name = "관광체육과 동물보호팀",
            district = "서울특별시 용산구",
            phone = "02-1234-1234"
        )
    )

    ExtraHomeDepartmentScreen(departments = dummyDepartments)
}