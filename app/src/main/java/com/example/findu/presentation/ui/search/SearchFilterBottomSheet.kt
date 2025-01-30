package com.example.findu.presentation.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBottomSheetBinding
import com.example.findu.presentation.ui.search.adapter.SearchFilterBreedRVAdapter
import com.example.findu.presentation.ui.search.adapter.SearchFilterLocationRVAdapter
import com.example.findu.presentation.ui.search.data.LocationData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.util.Calendar

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSearchFilterBottomSheetBinding
    private lateinit var breedAdapter: SearchFilterBreedRVAdapter
    private lateinit var cityAdapter: SearchFilterLocationRVAdapter
    private lateinit var guAdapter: SearchFilterLocationRVAdapter

    private val breeds =
        listOf("리트리버", "말티즈", "불독", "사모예드", "시츄", "요크셔 테리어", "치와와", "포메라니안", "웰시코기")
    private val selectedBreeds = mutableListOf<String>()

    private val locations =
        listOf(
            "전체",
            "서울특별시",
            "부산광역시",
            "인천광역시",
            "세종특별자치시",
            "대전광역시",
            "울산광역시",
            "경기도",
            "강원특별자치도",
            "충청북도",
            "충청남도",
            "전북특별자치도",
            "전라남도",
            "경상북도",
            "경상남도",
            "제주특별자치도",
        )
    private var city: String? = null

    private val gus = listOf("전체", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구")
    private var gu: String? = null

    private val locationMap = LocationData.locationMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFilterBottomSheetBinding.inflate(inflater, container, false)
        binding.ivSearchFilterCloseBtn.setOnClickListener {
            dismiss()
        }
        binding.btnSearchFilterConfirm.setOnClickListener {
            applyFilters()
        }
        setCalender()
        setBreedSelector()
        setLocationSelector()
        return binding.root
    }


    private fun applyFilters() {
        val filters = mutableListOf<String>()

        val date = binding.tvSearchFilterDateInput.text.toString()
        if (date.isNotEmpty() && date != getString(R.string.search_filter_date_input)) {
            filters.add(date)
        }

        val species = listOf(
            binding.rbDog,
            binding.rbCat,
            binding.rbEtc
        ).find { it.isChecked }?.text.toString()

        if (species.isNotBlank()) {
            filters.add(species)
        }
        if (filters.contains("null"))
            filters.remove("null")

        val breed = selectedBreeds.toList()
        if (selectedBreeds.isNotEmpty()) {
            filters.addAll(breed)

        }


        city?.let { if (it.isNotEmpty()) filters.add(it) }
        gu?.let { if (it.isNotEmpty()) filters.add(it) }

        if (filters.isEmpty()) {
            dismiss()
            return
        }

        val bundle = Bundle().apply {
            putStringArrayList("selectedFilters", ArrayList(filters))
        }
        parentFragmentManager.setFragmentResult("filterResults", bundle)

        dismiss()
    }


    override fun getTheme(): Int = R.style.searchFilterBottomSheetDialogTheme

    private fun setLocationSelector() {
        binding.actvSearchFilterGu.isEnabled = false

        cityAdapter = SearchFilterLocationRVAdapter(locations, city) { newLocation ->
            city = newLocation
            updateSelectedLocation()

            if (newLocation == "전체") {
                binding.actvSearchFilterGu.isEnabled = false
                binding.actvSearchFilterGu.setText("")
                gu = null
            } else {
                binding.actvSearchFilterGu.isEnabled = true
                val guList = locationMap[newLocation] ?: listOf("")
                guAdapter = SearchFilterLocationRVAdapter(guList, gu) { newGu ->
                    gu = newGu
                    updateSelectedLocation()
                }
                binding.rvSearchFilterGu.adapter = guAdapter
                binding.actvSearchFilterGu.setText("")
            }

        }
        guAdapter = SearchFilterLocationRVAdapter(gus, gu) { newGu ->
            gu = newGu
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
            if (binding.actvSearchFilterGu.isEnabled) {
                if (binding.rvSearchFilterGu.visibility == View.GONE) {
                    binding.rvSearchFilterGu.visibility = View.VISIBLE
                    binding.actvSearchFilterGu.setBackgroundResource(R.drawable.bg_search_radius_8_up)
                } else {
                    binding.rvSearchFilterGu.visibility = View.GONE
                    binding.actvSearchFilterGu.setBackgroundResource(R.drawable.bg_search_radius_8)
                }
            }
        }
    }

    private fun updateSelectedLocation() {
        binding.actvSearchFilterCity.setText(city ?: "")
        binding.rvSearchFilterCity.visibility = View.GONE
        binding.actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)

        binding.actvSearchFilterGu.setText(gu ?: "")
        binding.rvSearchFilterGu.visibility = View.GONE
        binding.actvSearchFilterGu.setBackgroundResource(R.drawable.bg_search_radius_8)
    }

    private fun setBreedSelector() {
        breedAdapter =
            SearchFilterBreedRVAdapter(breeds, selectedBreeds) { updateSelectedBreeds() }
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