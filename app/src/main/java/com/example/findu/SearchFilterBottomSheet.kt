package com.example.findu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.example.findu.databinding.FragmentSearchFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.util.Calendar

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSearchFilterBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFilterBottomSheetBinding.inflate(layoutInflater)
        binding.ivSearchFilterCloseBtn.setOnClickListener {
            dismiss()
        }
        setCalender()
        return binding.root
    }

    private fun setCalender() {
        binding.tvSearchFilterDateInput.setOnClickListener {
            if (binding.cvSearchFilterCalender.visibility == View.GONE) {
                binding.cvSearchFilterCalender.visibility = View.VISIBLE
                binding.ivSearchFilterDateDropBtn.setImageResource(R.drawable.ic_search_filter_drop_up)
                binding.tvSearchFilterDateInput.setBackgroundResource(R.drawable.bg_search_filter_spinner_open)
            } else {
                binding.cvSearchFilterCalender.visibility = View.GONE
                binding.ivSearchFilterDateDropBtn.setImageResource(R.drawable.ic_search_filter_drop)
                binding.tvSearchFilterDateInput.setBackgroundResource(R.drawable.bg_search_filter_spinner_close)
            }
        }

        val startMonth: Calendar = Calendar.getInstance().apply {
            set(2022, Calendar.JANUARY, 1)
        }
        val endMonth: Calendar = Calendar.getInstance().apply {
            set(
                LocalDateTime.now().year,
                LocalDateTime.now().monthValue - 1,
                LocalDateTime.now().dayOfMonth
            )
        }

        with(binding.cvSearchFilterCalender) {
            setVisibleMonthRange(startMonth, endMonth)
            setCurrentMonth(endMonth)
            setSelectableDateRange(startMonth, endMonth)
        }
        val startSelectionDate = Calendar.getInstance()
        startSelectionDate.add(Calendar.MONTH, -1)
        val endSelectionDate = startSelectionDate.clone() as Calendar
        endSelectionDate.add(Calendar.DATE, 40)

        binding.cvSearchFilterCalender.setCalendarListener(object : CalendarListener {
            @SuppressLint("SetTextI18n")
            override fun onFirstDateSelected(startDate: Calendar) {
                binding.tvSearchFilterDateInput.text =
                    "${startDate.get(Calendar.YEAR)}.${startDate.get(Calendar.MONTH) + 1}.${startDate.get(Calendar.DAY_OF_MONTH)} ~ " // ✅ 시작 날짜 표시
            }

            @SuppressLint("SetTextI18n")
            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                binding.tvSearchFilterDateInput.text =
                    "${startDate.get(Calendar.YEAR)}.${startDate.get(Calendar.MONTH) + 1}.${startDate.get(Calendar.DAY_OF_MONTH)} ~ " +
                            "${endDate.get(Calendar.YEAR)}.${endDate.get(Calendar.MONTH) + 1}.${endDate.get(Calendar.DAY_OF_MONTH)}" // ✅ 시작~종료 날짜 범위 표시

            }
        })

    }

}