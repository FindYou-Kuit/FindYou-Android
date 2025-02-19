package com.example.findu.presentation.ui.search

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBottomSheetBinding
import com.example.findu.presentation.ui.search.BundleTag.FILTER_RESULTS
import com.example.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.example.findu.presentation.ui.search.adapter.SearchFilterBreedRVAdapter
import com.example.findu.presentation.ui.search.adapter.SearchFilterLocationRVAdapter
import com.example.findu.presentation.ui.search.model.LocationData
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.util.Calendar

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentSearchFilterBottomSheetBinding
    private lateinit var breedAdapter: SearchFilterBreedRVAdapter
    private lateinit var cityAdapter: SearchFilterLocationRVAdapter
    private lateinit var districtAdapter: SearchFilterLocationRVAdapter

    private var selectedStartDate: String? = null
    private var selectedEndDate: String? = null
    private var selectedSpecies: String? = null

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

    private val districtsList =
        listOf("전체", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구")
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

        binding.rgSearchSpeciesType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_dog -> {
                    selectedSpecies = "개"
                }

                R.id.rb_cat -> {
                    selectedSpecies = "고양이"
                }

                R.id.rb_etc -> {
                    selectedSpecies = "기타"
                }
            }
        }
    }

    private fun applyFilters() {

        val bundle = Bundle()
        val filterUiModel = SearchFilterUiModel(
            startDate = null,
            endDate = null,
            species = null,
            breeds = null,
            location = null
        )
        filterUiModel.startDate = selectedStartDate
        filterUiModel.endDate = selectedEndDate

        filterUiModel.species = selectedSpecies
        filterUiModel.breeds = selectedBreeds

        val location = selectedCity?.let { city ->
            city + selectedDistrict?.let { district ->
                " $district"
            }
        }?: ""

        filterUiModel.location = location
        bundle.putSerializable(SELECTED_FILTER_DATA, filterUiModel)

        parentFragmentManager.setFragmentResult(FILTER_RESULTS, bundle)
        dismiss()
    }

    override fun getTheme(): Int = R.style.searchFilterBottomSheetDialogTheme

    private fun setLocationSelector() {
        binding.actvSearchFilterDistrict.isEnabled = false

        cityAdapter = SearchFilterLocationRVAdapter(cityList, selectedCity) { newCity ->
            selectedCity = newCity
            updateSelectedLocation()

            if (newCity == "전체") {
                binding.actvSearchFilterDistrict.isEnabled = false
                binding.actvSearchFilterDistrict.setText("")
                selectedDistrict = null
            } else {
                binding.actvSearchFilterDistrict.isEnabled = true
                val districtList = locationMap[newCity] ?: listOf("")
                districtAdapter =
                    SearchFilterLocationRVAdapter(districtList, selectedDistrict) { newDistrict ->
                        selectedDistrict = newDistrict
                        updateSelectedLocation()
                    }
                binding.rvSearchFilterDistrict.adapter = districtAdapter
                binding.actvSearchFilterDistrict.setText("")
            }

        }
        districtAdapter =
            SearchFilterLocationRVAdapter(districtsList, selectedDistrict) { newDistrict ->
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
        binding.rvSearchFilterDistrict.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilterDistrict.adapter = districtAdapter

        binding.actvSearchFilterDistrict.setOnClickListener {
            toggleRecyclerViewVisibility(
                binding.rvSearchFilterDistrict,
                binding.actvSearchFilterDistrict
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

        binding.actvSearchFilterDistrict.setText(selectedDistrict ?: "")
        binding.rvSearchFilterDistrict.visibility = View.GONE
        binding.actvSearchFilterDistrict.setBackgroundResource(R.drawable.bg_search_radius_8)
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
            override fun onFirstDateSelected(startDate: Calendar) {
                binding.tvSearchFilterDateInput.text = getString(
                    R.string.date_single,
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH) + 1,
                    startDate.get(Calendar.DAY_OF_MONTH)
                )
                binding.tvSearchFilterDateInput.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray6
                    )
                )
            }

            override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                binding.tvSearchFilterDateInput.text = getString(
                    R.string.date_range,
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH) + 1,
                    startDate.get(Calendar.DAY_OF_MONTH),
                    endDate.get(Calendar.YEAR),
                    endDate.get(Calendar.MONTH) + 1,
                    endDate.get(Calendar.DAY_OF_MONTH)
                )
                binding.tvSearchFilterDateInput.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.gray6
                    )
                )
                selectedStartDate =
                    "${startDate.get(Calendar.YEAR)}-${startDate.get(Calendar.MONTH) + 1}-${
                        startDate.get(Calendar.DAY_OF_MONTH)
                    }"
                selectedEndDate =
                    "${endDate.get(Calendar.YEAR)}-${endDate.get(Calendar.MONTH) + 1}-${
                        endDate.get(Calendar.DAY_OF_MONTH)
                    }"
            }
        })

    }

}