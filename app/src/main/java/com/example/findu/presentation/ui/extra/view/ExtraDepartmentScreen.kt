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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.extra.Department
import com.example.findu.domain.model.extra.Sido
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.extra.component.ExtraDepartmentItem
import com.example.findu.presentation.ui.extra.component.ExtraDistrictItem

@Composable
fun ExtraHomeDepartmentScreen(
    departments: List<Department>,
    selectedSido: Sido,
    selectedSigungu: String,
    sidoList: List<Sido>,
    sigunguList: List<String>,
    onSidoSelected: (Sido) -> Unit,
    onSigunguSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = {}
) {
    Column(modifier = modifier) {
        FindUTopAppBar(
            title = R.string.home_extra_department,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = popBackStack
        )
        Row(modifier = Modifier.padding(20.dp)) {
            ExtraDistrictItem(
                modifier = Modifier.weight(1f),
                selectedDistrict = selectedSido.name,
                districtOptions = sidoList,
                onDistrictSelected = onSidoSelected,
                hint = stringResource(R.string.home_extra_sido),
                itemToString = {it.name}
            )
            Spacer(modifier = Modifier.width(10.dp))
            ExtraDistrictItem(
                modifier = Modifier.weight(1f),
                selectedDistrict = selectedSigungu,
                districtOptions = sigunguList,
                onDistrictSelected = onSigunguSelected,
                hint = stringResource(R.string.home_extra_sigungu),
                itemToString = {it}

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

    ExtraHomeDepartmentScreen(
        departments = dummyDepartments,
        sidoList = emptyList(),
        sigunguList = emptyList(),
        onSidoSelected = {},
        onSigunguSelected = {},
        selectedSido = Sido(id=0,name=""),
        selectedSigungu = "",
    )
}