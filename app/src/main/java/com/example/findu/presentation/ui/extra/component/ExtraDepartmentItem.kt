package com.example.findu.presentation.ui.extra.component

import android.content.Intent
import android.net.Uri
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
import com.example.findu.R
import com.example.findu.domain.model.extra.Department
import com.example.findu.domain.model.extra.VolunteerWork
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ExtraDepartmentItem(
    department: Department,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_DIAL, "tel:${department.phone}".toUri())


    Column(modifier = modifier.padding(20.dp)) {
        Text(text = department.name)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Tel) ${department.phone} >", modifier = Modifier.noRippleClickable {
            context.startActivity(intent)
        })
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = department.district)

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
private fun ExtraDepartmentItemPreview() {
    ExtraDepartmentItem(
        department = Department(
            name = "관광체육과 동물보호팀",
            district = "서울특별시 용산구",
            phone = "02-1234-1234"
        )
    )
}