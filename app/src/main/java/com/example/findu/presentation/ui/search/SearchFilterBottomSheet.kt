package com.example.findu.presentation.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBottomSheetBinding
import com.example.findu.presentation.ui.search.adapter.SearchFilterBreedRVAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.util.Calendar

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSearchFilterBottomSheetBinding
    private lateinit var breedAdapter: SearchFilterBreedRVAdapter
    private val breeds = listOf("리트리버", "말티즈", "불독", "사모예드", "시츄", "요크셔 테리어", "치와와", "포메라니안", "웰시코기")

    private val selectedBreeds = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFilterBottomSheetBinding.inflate(inflater, container, false)
        binding.ivSearchFilterCloseBtn.setOnClickListener {
            dismiss()
        }
        binding.btnSearchFilterConfirm.setOnClickListener{
            dismiss()
        }
        setCalender()
        setBreedSelector()
        return binding.root
    }
    override fun getTheme(): Int = R.style.searchFilterBottomSheetDialogTheme

    private fun setBreedSelector() {
        breedAdapter = SearchFilterBreedRVAdapter(breeds, selectedBreeds) { updateSelectedBreeds() }
        binding.rvSearchFilterBreeds.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilterBreeds.adapter = breedAdapter

        binding.actvSearchFilterBreed.setOnClickListener {
            if (binding.rvSearchFilterBreeds.visibility == View.GONE) {
                binding.rvSearchFilterBreeds.visibility = View.VISIBLE
                binding.actvSearchFilterBreed.setBackgroundResource(R.drawable.bg_search_radius_8_up)

            } else {
                binding.rvSearchFilterBreeds.visibility = View.GONE
                binding.actvSearchFilterBreed.setBackgroundResource(R.drawable.bg_search_radius_8)
            }
        }
    }

    private fun updateSelectedBreeds() {
        binding.actvSearchFilterBreed.setText(selectedBreeds.joinToString(", "))
    }


    private fun setCalender() {
        binding.tvSearchFilterDateInput.setOnClickListener {
            if (binding.cvSearchFilterCalender.visibility == View.GONE) {
                binding.cvSearchFilterCalender.visibility = View.VISIBLE
                binding.ivSearchFilterDateDropBtn.setImageResource(R.drawable.ic_search_filter_drop_up)
                binding.tvSearchFilterDateInput.setBackgroundResource(R.drawable.bg_search_radius_8_up)
            } else {
                binding.cvSearchFilterCalender.visibility = View.GONE
                binding.ivSearchFilterDateDropBtn.setImageResource(R.drawable.ic_search_filter_drop)
                binding.tvSearchFilterDateInput.setBackgroundResource(R.drawable.bg_search_radius_8)
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
                    "${startDate.get(Calendar.YEAR)}.${startDate.get(Calendar.MONTH) + 1}.${
                        startDate.get(
                            Calendar.DAY_OF_MONTH
                        )
                    } ~ "
                binding.tvSearchFilterDateInput.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray6
                    )
                )

            }
            @SuppressLint("SetTextI18n")
            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                binding.tvSearchFilterDateInput.text =
                    "${startDate.get(Calendar.YEAR)}.${startDate.get(Calendar.MONTH) + 1}.${
                        startDate.get(
                            Calendar.DAY_OF_MONTH
                        )
                    } ~ " +
                            "${endDate.get(Calendar.YEAR)}.${endDate.get(Calendar.MONTH) + 1}.${
                                endDate.get(
                                    Calendar.DAY_OF_MONTH
                                )
                            }"
                binding.tvSearchFilterDateInput.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray6
                    )
                )

            }
        })

    }

}