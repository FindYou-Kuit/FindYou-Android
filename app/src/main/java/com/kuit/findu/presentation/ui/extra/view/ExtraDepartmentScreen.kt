package com.kuit.findu.presentation.ui.extra.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kuit.findu.R
import com.kuit.findu.domain.model.extra.Department
import com.kuit.findu.presentation.ui.base.FindUTopAppBar
import com.kuit.findu.presentation.ui.extra.component.ExtraDepartmentItem

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
        //TODO: 지역 선택 드롭다운 구현

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