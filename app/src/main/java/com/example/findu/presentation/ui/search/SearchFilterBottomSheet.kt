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
import com.example.findu.presentation.ui.search.data.LocationData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.util.Calendar

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSearchFilterBottomSheetBinding
    private lateinit var breedAdapter: SearchFilterBreedRVAdapter
    private lateinit var cityAdapter: SearchFilterLocationRVAdapter
    private lateinit var districtAdapter: SearchFilterLocationRVAdapter

    private val breedList =
        listOf("리트리버", "말티즈", "불독", "사모예드", "시츄", "요크셔 테리어", "치와와", "포메라니안", "웰시코기")
    private val selectedBreeds = mutableListOf<String>()

    private val cityList =
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
    private var selectedCity: String? = null

    private val districtsList = listOf("전체", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구")
    private var selectedDistrict: String? = null

    private val locationMap = LocationData.locationMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFilterBottomSheetBinding.inflate(inflater, container, false)
        initListeners()
        setCalender()
        setBreedSelector()
        setLocationSelector()
        return binding.root
    }
    private fun initListeners() {
        binding.ivSearchFilterCloseBtn.setOnClickListener { dismiss() }
        binding.btnSearchFilterConfirm.setOnClickListener { applyFilters() }
    }

    private fun applyFilters() {
        val filterList = mutableListOf<String>()

        val selectedDate = binding.tvSearchFilterDateInput.text.toString()
        if (selectedDate.isNotEmpty() && selectedDate != getString(R.string.search_filter_date_input)) {
            filterList.add(selectedDate)
        }

        val selectedSpecies = listOf(
            binding.rbDog,
            binding.rbCat,
            binding.rbEtc
        ).find { it.isChecked }?.text.toString()

        if (selectedSpecies.isNotBlank()) {
            filterList.add(selectedSpecies)
        }
        if (filterList.contains("null"))
            filterList.remove("null")

        if (selectedBreeds.isNotEmpty()) {
            filterList.addAll(selectedBreeds)

        }

        selectedCity?.let { if (it.isNotEmpty()) filterList.add(it) }
        selectedDistrict?.let { if (it.isNotEmpty()) filterList.add(it) }

        if (filterList.isEmpty()) {
            dismiss()
            return
        }

        val bundle = Bundle().apply {
            putStringArrayList("selectedFilters", ArrayList(filterList))
        }
        parentFragmentManager.setFragmentResult("filterResults", bundle)

        dismiss()
    }

    override fun getTheme(): Int = R.style.searchFilterBottomSheetDialogTheme

    private fun setLocationSelector() {
        binding.actvSearchFilterGu.isEnabled = false

        cityAdapter = SearchFilterLocationRVAdapter(cityList, selectedCity) { newCity ->
            selectedCity = newCity
            updateSelectedLocation()

            if (newCity == "전체") {
                binding.actvSearchFilterGu.isEnabled = false
                binding.actvSearchFilterGu.setText("")
                selectedDistrict = null
            } else {
                binding.actvSearchFilterGu.isEnabled = true
                val districtList = locationMap[newCity] ?: listOf("")
                districtAdapter = SearchFilterLocationRVAdapter(districtList, selectedDistrict) { newDistrict ->
                    selectedDistrict = newDistrict
                    updateSelectedLocation()
                }
                binding.rvSearchFilterGu.adapter = districtAdapter
                binding.actvSearchFilterGu.setText("")
            }

        }
        districtAdapter = SearchFilterLocationRVAdapter(districtsList, selectedDistrict) { newDistrict ->
            selectedDistrict = newDistrict
            updateSelectedLocation()
        }

        binding.rvSearchFilterCity.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilterCity.adapter = cityAdapter

        binding.actvSearchFilterCity.setOnClickListener {
            toggleRecyclerViewVisibility(
                binding.rvSearchFilterCity,
                binding.actvSearchFilterCity
            )
        }
        binding.rvSearchFilterGu.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilterGu.adapter = districtAdapter

        binding.actvSearchFilterGu.setOnClickListener {
            toggleRecyclerViewVisibility(
                binding.rvSearchFilterGu,
                binding.actvSearchFilterGu
            )
        }
    }
    private fun toggleRecyclerViewVisibility(recyclerView: View, triggerView: View) {
        if (recyclerView.visibility == View.GONE) {
            recyclerView.visibility = View.VISIBLE
            triggerView.setBackgroundResource(R.drawable.bg_search_radius_8_up)
        } else {
            recyclerView.visibility = View.GONE
            triggerView.setBackgroundResource(R.drawable.bg_search_radius_8)
        }
    }

    private fun updateSelectedLocation() {
        binding.actvSearchFilterCity.setText(selectedCity ?: "")
        binding.rvSearchFilterCity.visibility = View.GONE
        binding.actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)

        binding.actvSearchFilterGu.setText(selectedDistrict ?: "")
        binding.rvSearchFilterGu.visibility = View.GONE
        binding.actvSearchFilterGu.setBackgroundResource(R.drawable.bg_search_radius_8)
    }

    private fun setBreedSelector() {
        breedAdapter =
            SearchFilterBreedRVAdapter(breedList, selectedBreeds) { updateSelectedBreeds() }
        binding.rvSearchFilterBreeds.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilterBreeds.adapter = breedAdapter

        binding.actvSearchFilterBreed.setOnClickListener {
            toggleRecyclerViewVisibility(
                binding.rvSearchFilterBreeds,
                binding.actvSearchFilterBreed
            )
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