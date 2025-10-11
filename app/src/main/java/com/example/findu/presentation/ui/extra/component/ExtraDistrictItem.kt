package com.example.findu.presentation.ui.extra.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtraDistrictItem(
    selectedDistrict: String,
    districtOptions: List<String>,
    onDistrictSelected: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        Row(
            modifier = Modifier
                .menuAnchor()
                .border(width = 1.dp, shape = RoundedCornerShape(30.dp), color = FindUTheme.colors.gray4)
                .roundedBackgroundWithPadding(
                    backgroundColor = FindUTheme.colors.white,
                    cornerRadius = 30.dp,
                    padding = PaddingValues(start = 20.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedDistrict.takeIf { it.isNotEmpty() } ?: hint,
                color = if (selectedDistrict.isEmpty()) FindUTheme.colors.gray4 else FindUTheme.colors.gray6,
                style = FindUTheme.typography.body2SemiBold14
            )
            Spacer(modifier = Modifier.weight(1f))
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.Transparent)
        ) {
            districtOptions.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onDistrictSelected(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}


@Preview
@Composable
private fun ExtraDistrictItemPreview() {
    FindUTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            var selectedDistrict by remember { mutableStateOf("") }
            val options = listOf("서울특별시", "부산광역시", "인천광역시", "제주자치도")

            Column(modifier = Modifier.fillMaxSize()) {
                ExtraDistrictItem(
                    selectedDistrict = selectedDistrict,
                    districtOptions = options,
                    hint = stringResource(R.string.home_extra_sido),
                    onDistrictSelected = { selectedDistrict = it }
                )
            }
        }
    }
}