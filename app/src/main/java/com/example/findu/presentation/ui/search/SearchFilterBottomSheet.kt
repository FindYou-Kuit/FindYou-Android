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
import com.example.findu.presentation.ui.search.adapter.SearchFilterLocationRVAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.util.Calendar

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSearchFilterBottomSheetBinding
    private lateinit var breedAdapter: SearchFilterBreedRVAdapter
    private lateinit var cityAdapter: SearchFilterLocationRVAdapter
    private lateinit var guAdapter: SearchFilterLocationRVAdapter

    private val breeds = listOf("리트리버", "말티즈", "불독", "사모예드", "시츄", "요크셔 테리어", "치와와", "포메라니안", "웰시코기")
    private val selectedBreeds = mutableListOf<String>()

    private val locations = listOf("서울특별시", "인천광역시", "세종특별자치시", "울산광역시", "강원특별자치도", "충청남도", "전라남도", "경상남도", "부산광역시")
    private var selectedLocation: String? = null

    private val gus = listOf("강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구")
    private var selectedGu: String? = null

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
        setLocationSelector()
        return binding.root
    }
    override fun getTheme(): Int = R.style.searchFilterBottomSheetDialogTheme

    private fun setLocationSelector() {
        cityAdapter = SearchFilterLocationRVAdapter(locations, selectedLocation) { newLocation ->
            selectedLocation = newLocation
            updateSelectedLocation()
        }

        guAdapter = SearchFilterLocationRVAdapter(gus, selectedGu) { newGu ->
            selectedGu = newGu
            updateSelectedLocation()
        }

        binding.rvSearchFilterCity.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilterCity.adapter = cityAdapter

        binding.actvSearchFilterCity.setOnClickListener {
            if (binding.rvSearchFilterCity.visibility == View.GONE) {
                binding.rvSearchFilterCity.visibility = View.VISIBLE
                binding.actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8_up)
            } else {
                binding.rvSearchFilterCity.visibility = View.GONE
                binding.actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)
            }
        }
        binding.rvSearchFilterGu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilterGu.adapter = guAdapter

        binding.actvSearchFilterGu.setOnClickListener {
            if (binding.rvSearchFilterGu.visibility == View.GONE) {
                binding.rvSearchFilterGu.visibility = View.VISIBLE
                binding.actvSearchFilterGu.setBackgroundResource(R.drawable.bg_search_radius_8_up)
            } else {
                binding.rvSearchFilterGu.visibility = View.GONE
                binding.actvSearchFilterGu.setBackgroundResource(R.drawable.bg_search_radius_8)
            }
        }
    }

    private fun updateSelectedLocation() {
        binding.actvSearchFilterCity.setText(selectedLocation ?: "")
        binding.rvSearchFilterCity.visibility = View.GONE
        binding.actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)

        binding.actvSearchFilterGu.setText(selectedGu ?: "")
        binding.rvSearchFilterGu.visibility = View.GONE
        binding.actvSearchFilterGu.setBackgroundResource(R.drawable.bg_search_radius_8)
    }

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