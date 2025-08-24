package com.example.findu.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.search.adapter.SearchFilterLocationRVAdapter
import com.example.findu.presentation.ui.search.dialog.SearchFilterDateDialog
import com.example.findu.presentation.ui.search.model.LocationData
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchFilterFragment : Fragment() {

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    private val fmt = DateTimeFormatter.ISO_DATE
    private val filterModel = SearchFilterUiModel()

    private var selectedSpecies: String? = null
    private var breedList: List<String> = emptyList()

    private lateinit var cityAdapter: SearchFilterLocationRVAdapter
    private lateinit var districtAdapter: SearchFilterLocationRVAdapter
    private var selectedCity: String? = null
    private var selectedDistrict: String? = null

    private val cityList =
        listOf(
            "전체", "서울특별시", "부산광역시","인천광역시", "세종특별자치시", "대전광역시", "울산광역시", "경기도", "강원특별자치도", "충청북도",
            "충청남도", "전북특별자치도", "전라남도", "경상북도", "경상남도", "제주특별자치도",
        )

    private val districtsList =
        listOf("전체", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구")
    private val locationMap = LocationData.locationMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        setUpLocationSelector()
        setUpCalender()

    }

    private fun setUpCalender()  = with(binding){
        clSearchFilterDateStart.setOnClickListener {
            SearchFilterDateDialog(Type.DATE_START, filterModel) { updated ->
                tvSearchFilterDateStart.text = updated.startDate
                tvSearchFilterDateStart.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.gray6)
                )
                updated.endDate?.let { end ->
                    if (LocalDate.parse(end, fmt)
                            .isBefore(LocalDate.parse(updated.startDate, fmt))
                    ) {
                        filterModel.endDate = null
                        tvSearchFilterDateEnd.text =
                            getString(R.string.search_filter_date_input_end)
                        tvSearchFilterDateEnd.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.gray4)
                        )
                    }
                }
            }.show(parentFragmentManager, "date_start")
        }

        clSearchFilterDateEnd.setOnClickListener {
            SearchFilterDateDialog(Type.DATE_END, filterModel) { updated ->
                tvSearchFilterDateEnd.text = updated.endDate
                tvSearchFilterDateEnd.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.gray6)
                )
            }.show(parentFragmentManager, "date_end")
        }

    }

    private fun setUpLocationSelector() = with(binding){
        actvSearchFilterDistrict.isEnabled = false

        cityAdapter = SearchFilterLocationRVAdapter(cityList, selectedCity) { newCity ->
            selectedCity = newCity
            updateSelectedLocation()

            if (newCity == "전체") {
                actvSearchFilterDistrict.isEnabled = false
                actvSearchFilterDistrict.setText("")
                selectedDistrict = null
            } else {
                actvSearchFilterDistrict.isEnabled = true
                val districtList = locationMap[newCity] ?: listOf("")
                districtAdapter =
                    SearchFilterLocationRVAdapter(districtList, selectedDistrict) { newDistrict ->
                        selectedDistrict = newDistrict
                        updateSelectedLocation()
                    }
                rvSearchFilterDistrict.adapter = districtAdapter
                actvSearchFilterDistrict.setText("")
            }
        }

        districtAdapter =
            SearchFilterLocationRVAdapter(districtsList, selectedDistrict) { newDistrict ->
                selectedDistrict = newDistrict
                updateSelectedLocation()
            }

        rvSearchFilterCity.layoutManager = LinearLayoutManager(requireContext())
        rvSearchFilterCity.adapter = cityAdapter

        actvSearchFilterCity.setOnClickListener {
            toggleRecyclerViewVisibility(
                flFilterCityContainer,
                actvSearchFilterCity
            )
        }
        rvSearchFilterDistrict.layoutManager = LinearLayoutManager(requireContext())
        rvSearchFilterDistrict.adapter = districtAdapter

        actvSearchFilterDistrict.setOnClickListener {
            toggleRecyclerViewVisibility(
                flFilterDistrictContainer,
                actvSearchFilterDistrict
            )
        }
    }

    private fun toggleRecyclerViewVisibility(container: View, triggerView: View) = with(binding){
        if (container.visibility == View.GONE) {
            container.visibility = View.VISIBLE
            triggerView.setBackgroundResource(R.drawable.bg_search_radius_8_up)
        } else {
            container.visibility = View.GONE
            triggerView.setBackgroundResource(R.drawable.bg_search_radius_8)
        }
    }

    private fun updateSelectedLocation() = with(binding) {
        actvSearchFilterCity.setText(selectedCity ?: "")
        actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)

        actvSearchFilterDistrict.setText(selectedDistrict ?: "")
        actvSearchFilterDistrict.setBackgroundResource(R.drawable.bg_search_radius_8)
    }

    private fun initListener() = with(binding) {

        rgSearchSpeciesType.setOnCheckedChangeListener { _, checkedId ->
            val defaultColor = ContextCompat.getColor(requireContext(), R.color.gray6)
            val defaultStyle = R.style.TextAppearance_FindU_Body2_SB_14

            with(rbSearchFilterDog) {
                setTextAppearance(defaultStyle)
                setTextColor(defaultColor)
            }
            with(rbSearchFilterCat) {
                setTextAppearance(defaultStyle)
                setTextColor(defaultColor)
            }
            with(rbSearchFilterEtc) {
                setTextAppearance(defaultStyle)
                setTextColor(defaultColor)
            }

            when (checkedId) {
                R.id.rb_search_filter_dog -> {
                    // viewModel.selectSpeciesType(SpeciesType.DOG)
                    selectedSpecies = "개"
                    filterModel.species = SpeciesType.DOG.name
                    with(rbSearchFilterDog){
                        setTextAppearance(R.style.TextAppearance_FindU_Body1_SB_16)
                        setTextColor( ContextCompat.getColor(requireContext(), R.color.main_color))
                    }

                }

                R.id.rb_search_filter_cat -> {
                    // viewModel.selectSpeciesType(SpeciesType.CAT)
                    selectedSpecies = "고양이"
                    filterModel.species = SpeciesType.CAT.name
                    with(rbSearchFilterCat){
                        setTextAppearance(R.style.TextAppearance_FindU_Body1_SB_16)
                        setTextColor( ContextCompat.getColor(requireContext(), R.color.main_color))
                    }
                }

                R.id.rb_search_filter_etc -> {
                    // viewModel.selectSpeciesType(SpeciesType.ETC)
                    selectedSpecies = "기타"
                    filterModel.species = SpeciesType.ETC.name
                    with(rbSearchFilterEtc){
                        setTextAppearance(R.style.TextAppearance_FindU_Body1_SB_16)
                        setTextColor( ContextCompat.getColor(requireContext(), R.color.main_color))
                    }
                }


            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}