package com.example.findu.presentation.ui.report.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.findu.R
import com.example.findu.databinding.ItemReportBreedsBinding
import com.example.findu.presentation.ui.report.model.ReportDummys

class ReportBreedAdapter(
    context: Context,
    items: List<String>
) : ArrayAdapter<String>(
    context,
    R.layout.item_report_breeds,
    R.id.tv_item_breed,
    items
) {
    // TODO: 작동 안되는 듯함. 수정 필요
    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val itemBinding = if (convertView == null) {
            val itemBinding = ItemReportBreedsBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
            itemBinding.root.tag = itemBinding
            itemBinding
        } else {
            convertView.tag as ItemReportBreedsBinding
        }

        if (position == ReportDummys.dummyBreeds.size - 1) {
            itemBinding.vItemBreedDivider.visibility = View.GONE
        } else {
            itemBinding.vItemBreedDivider.visibility = View.VISIBLE
        }
        return itemBinding.root

    }
}