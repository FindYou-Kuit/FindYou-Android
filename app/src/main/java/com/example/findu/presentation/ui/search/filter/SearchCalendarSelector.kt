package com.example.findu.presentation.ui.search.filter

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.presentation.ui.search.dialog.SearchFilterDateDialog
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchCalendarSelector(
    private val binding: FragmentSearchFilterBinding,
    private val filterModel: SearchFilterUiModel,
    private val fragmentManager: FragmentManager
) {
    private val fmt = DateTimeFormatter.ISO_DATE

    fun init() = with(binding) {
        clSearchFilterDateStart.setOnClickListener {
            SearchFilterDateDialog(Type.DATE_START, filterModel) { updated ->
                val picked = updated.startDate
                filterModel.startDate = picked
                tvSearchFilterDateStart.text = picked
                tvSearchFilterDateStart.setTextColor(ContextCompat.getColor(root.context, R.color.gray6))

                val end = filterModel.endDate?.let { LocalDate.parse(it, fmt) }
                val start = LocalDate.parse(picked, fmt)
                if (end == null || end.isBefore(start)) {
                    filterModel.endDate = picked
                    tvSearchFilterDateEnd.text = picked
                    tvSearchFilterDateEnd.setTextColor(ContextCompat.getColor(root.context, R.color.gray6))
                }
            }.show(fragmentManager, "date_start")
        }

        clSearchFilterDateEnd.setOnClickListener {
            SearchFilterDateDialog(Type.DATE_END, filterModel) { updated ->
                val picked = updated.endDate
                filterModel.endDate = picked
                tvSearchFilterDateEnd.text = picked
                tvSearchFilterDateEnd.setTextColor(ContextCompat.getColor(root.context, R.color.gray6))

                val start = filterModel.startDate?.let { LocalDate.parse(it, fmt) }
                val end = LocalDate.parse(picked, fmt)
                if (start == null || start.isAfter(end)) {
                    filterModel.startDate = picked
                    tvSearchFilterDateStart.text = picked
                    tvSearchFilterDateStart.setTextColor(ContextCompat.getColor(root.context, R.color.gray6))
                }
            }.show(fragmentManager, "date_end")
        }
    }

    fun reset() = with(binding){
        tvSearchFilterDateStart.text = root.context.getString(R.string.search_filter_date_input_start)
        tvSearchFilterDateEnd.text = root.context.getString(R.string.search_filter_date_input_end)
        filterModel.startDate = null
        filterModel.endDate = null
    }
}