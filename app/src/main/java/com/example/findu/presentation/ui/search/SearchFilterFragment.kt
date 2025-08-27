package com.example.findu.presentation.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.search.BundleTag.FILTER_RESULTS
import com.example.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.example.findu.presentation.ui.search.adapter.SearchBreedRVAdapter
import com.example.findu.presentation.ui.search.adapter.SearchFilterLocationRVAdapter
import com.example.findu.presentation.ui.search.dialog.SearchFilterDateDialog
import com.example.findu.presentation.ui.search.model.LocationData
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.Type
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchFilterFragment : Fragment() {

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    private val fmt = DateTimeFormatter.ISO_DATE
    private val filterModel = SearchFilterUiModel()

    private var selectedSpecies: String? = null

    private lateinit var cityAdapter: SearchFilterLocationRVAdapter
    private lateinit var districtAdapter: SearchFilterLocationRVAdapter
    private var selectedCity: String? = null
    private var selectedDistrict: String? = null

    private val cityList =
        listOf(
            "전체", "서울특별시", "부산광역시", "인천광역시", "세종특별자치시", "대전광역시", "울산광역시", "경기도", "강원특별자치도", "충청북도",
            "충청남도", "전북특별자치도", "전라남도", "경상북도", "경상남도", "제주특별자치도",
        )

    private val districtsList =
        listOf("전체", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구")
    private val locationMap = LocationData.locationMap

    private val breedsBySpecies: Map<SpeciesType, List<String>> = mapOf(
        SpeciesType.DOG to listOf(
            "말티즈", "푸들", "포메라니안", "시바", "코기", "진돗개", "리트리버", "치와와", "비숑", "시츄", "그레이하운드"
        ),
        SpeciesType.CAT to listOf(
            "코리안숏헤어", "러시안블루", "스코티쉬폴드", "먼치킨", "노르웨이지안숲", "터키시앙고라", "렉돌", "페르시안"
        ),
        SpeciesType.ETC to listOf(
            "햄스터", "고슴도치", "앵무새", "토끼", "페럿", "거북이"
        )
    )

    private val selectedBreedList = mutableListOf<String>()
    private val maxBreedCount = 10
    private lateinit var breedRvAdapter: SearchBreedRVAdapter
    private var isBreedDropdownOpen = false
    private var suppressBreedTextWatcher = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
    }

    private fun initViews() {
        setUpLocationSelector()
        setUpCalender()
        setUpSpecies()
        setUpBreedSelector()
        setBreedFieldEnabled(false)
        renderBreedText()
    }

    private fun token(): String = binding.actvSearchFilterBreed.text?.toString().orEmpty().substringAfterLast(",").trim()

    private fun rotateArrow(view: ImageView, open: Boolean) {
        val target = if (open) 180f else 0f
        view.animate().rotation(target).setDuration(150).start()
    }

    private fun renderBreedText() = with(binding) {
        suppressBreedTextWatcher = true
        actvSearchFilterBreed.setText(selectedBreedList.joinToString(", "))
        actvSearchFilterBreed.setSelection(actvSearchFilterBreed.text?.length ?: 0)
        suppressBreedTextWatcher = false
    }

    private fun setBreedFieldEnabled(enabled: Boolean) = with(binding) {
        actvSearchFilterBreed.isEnabled = enabled
        actvSearchFilterBreed.alpha = if (enabled) 1f else 0.5f
        if (!enabled) setBreedDropdown(false)
    }

    private fun setUpBreedSelector() = with(binding) {
        rvSearchFilterBreed.layoutManager = LinearLayoutManager(requireContext())
        tvSearchFilterBreedCount.text = getString(R.string.search_bottom_sheet_breed_count, 0)
        cgSelectedBreeds.removeAllViews()

        actvSearchFilterBreed.setOnClickListener {
            flFilterCityContainer.isGone = true
            flFilterDistrictContainer.isGone = true
            if (!actvSearchFilterBreed.isEnabled || !this@SearchFilterFragment::breedRvAdapter.isInitialized) return@setOnClickListener
            breedRvAdapter.filter.filter(token())
            setBreedDropdown(!flFilterBreedContainer.isVisible)
        }

        actvSearchFilterBreed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (suppressBreedTextWatcher || !actvSearchFilterBreed.isEnabled || !this@SearchFilterFragment::breedRvAdapter.isInitialized) {
                    setBreedDropdown(false); return
                }
                breedRvAdapter.filter.filter(token())
                setBreedDropdown(true)
            }
        })

    }

    private fun setBreedDropdown(open: Boolean) = with(binding) {
        flFilterBreedContainer.isVisible = open
        actvSearchFilterBreed.setBackgroundResource(if (open) R.drawable.bg_search_radius_8_up else R.drawable.bg_search_radius_8)
        rotateArrow(ivBreedArrow, open)
        isBreedDropdownOpen = open
    }

    private fun setUpSpecies() = with(binding) {
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.gray6)
        val defaultStyle = R.style.TextAppearance_FindU_Body2_SB_14
        val hiColor = ContextCompat.getColor(requireContext(), R.color.main_color)
        val hiStyle = R.style.TextAppearance_FindU_Body1_SB_16

        fun styleDefault() {
            rbSearchFilterDog.setTextAppearance(defaultStyle); rbSearchFilterDog.setTextColor(defaultColor)
            rbSearchFilterCat.setTextAppearance(defaultStyle); rbSearchFilterCat.setTextColor(defaultColor)
            rbSearchFilterEtc.setTextAppearance(defaultStyle); rbSearchFilterEtc.setTextColor(defaultColor)
        }

        fun applySpecies(species: SpeciesType?) {
            selectedBreedList.clear()
            updateBreedChipsAndCounter()
            setBreedFieldEnabled(species != null)
            setBreedDropdown(false)

            styleDefault()
            when (species) {
                SpeciesType.DOG -> {
                    selectedSpecies = "개"; filterModel.species = SpeciesType.DOG.name
                    rbSearchFilterDog.setTextAppearance(hiStyle); rbSearchFilterDog.setTextColor(hiColor)
                    setBreedData(breedsBySpecies[SpeciesType.DOG].orEmpty())
                }
                SpeciesType.CAT -> {
                    selectedSpecies = "고양이"; filterModel.species = SpeciesType.CAT.name
                    rbSearchFilterCat.setTextAppearance(hiStyle); rbSearchFilterCat.setTextColor(hiColor)
                    setBreedData(breedsBySpecies[SpeciesType.CAT].orEmpty())
                }
                SpeciesType.ETC -> {
                    selectedSpecies = "기타"; filterModel.species = SpeciesType.ETC.name
                    rbSearchFilterEtc.setTextAppearance(hiStyle); rbSearchFilterEtc.setTextColor(hiColor)
                    setBreedData(breedsBySpecies[SpeciesType.ETC].orEmpty())
                }
                null -> {
                    selectedSpecies = null; filterModel.species = null
                    if (this@SearchFilterFragment::breedRvAdapter.isInitialized) rvSearchFilterBreed.adapter = null
                }
            }
        }

        rgSearchSpeciesType.setOnCheckedChangeListener { _, id ->
            applySpecies(
                when (id) {
                    R.id.rb_search_filter_dog -> SpeciesType.DOG
                    R.id.rb_search_filter_cat -> SpeciesType.CAT
                    R.id.rb_search_filter_etc -> SpeciesType.ETC
                    else -> null
                }
            )
        }

        when (rgSearchSpeciesType.checkedRadioButtonId) {
            R.id.rb_search_filter_dog -> applySpecies(SpeciesType.DOG)
            R.id.rb_search_filter_cat -> applySpecies(SpeciesType.CAT)
            R.id.rb_search_filter_etc -> applySpecies(SpeciesType.ETC)
            else -> applySpecies(null)
        }
    }


    private fun showBreedHintAndClear() = with(binding) {
        actvSearchFilterBreed.setText("")
        actvSearchFilterBreed.hint = getString(R.string.search_filter_breed_hint)
    }


    private fun setBreedData(breeds: List<String>) = with(binding) {
        setBreedFieldEnabled(true)

        breedRvAdapter = SearchBreedRVAdapter(
            allItems = breeds,
            isSelected = { name -> selectedBreedList.contains(name) },
            onPick = { picked ->
                if (picked in selectedBreedList) removeBreed(picked) else addBreed(picked)
                breedRvAdapter.refreshSelections()
                setBreedDropdown(true)
            }
        )
        rvSearchFilterBreed.adapter = breedRvAdapter
        breedRvAdapter.filter.filter(token())

    }

    private fun clearBreedAdapter() = with(binding) {
        if (this@SearchFilterFragment::breedRvAdapter.isInitialized) {
            rvSearchFilterBreed.adapter = null
        }
    }

    private fun addBreed(breed: String) {
        if (selectedBreedList.contains(breed)) return
        if (selectedBreedList.size >= maxBreedCount) {
            Toast.makeText(requireContext(), "최대 10개까지 선택할 수 있어요.", Toast.LENGTH_SHORT).show()
            return
        }
        selectedBreedList.add(breed)
        updateBreedChipsAndCounter()
    }

    private fun updateBreedChipsAndCounter() = with(binding) {
        tvSearchFilterBreedCount.text = getString(R.string.search_bottom_sheet_breed_count, selectedBreedList.size)
        cgSelectedBreeds.removeAllViews()
        selectedBreedList.forEach { b ->
            val chip = layoutInflater.inflate(R.layout.item_search_breed_chip, cgSelectedBreeds, false) as Chip
            chip.text = b
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener { removeBreed(b) }
            cgSelectedBreeds.addView(chip)
        }
        renderBreedText()
    }

    private fun removeBreed(breed: String) {
        selectedBreedList.remove(breed)
        updateBreedChipsAndCounter()
        if (this::breedRvAdapter.isInitialized) {
            breedRvAdapter.refreshSelections()
        }
        if (isBreedDropdownOpen) setBreedDropdown(true)
    }

    private fun setUpCalender() = with(binding) {
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

    private fun setUpLocationSelector() = with(binding) {
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
                actvSearchFilterCity,
                ivCityArrow
            )
        }
        rvSearchFilterDistrict.layoutManager = LinearLayoutManager(requireContext())
        rvSearchFilterDistrict.adapter = districtAdapter

        actvSearchFilterDistrict.setOnClickListener {
            toggleRecyclerViewVisibility(
                flFilterDistrictContainer,
                actvSearchFilterDistrict,
                ivDistrictArrow
            )
        }
    }

    private fun toggleRecyclerViewVisibility(container: View, triggerView: View, arrow: ImageView) = with(binding) {
        val open = container.visibility == View.GONE
        container.visibility = if (open) View.VISIBLE else View.GONE
        triggerView.setBackgroundResource(if (open) R.drawable.bg_search_radius_8_up else R.drawable.bg_search_radius_8)
        rotateArrow(arrow, open)
    }

    private fun updateSelectedLocation() = with(binding) {
        actvSearchFilterCity.setText(selectedCity ?: "")
        actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)

        actvSearchFilterDistrict.setText(selectedDistrict ?: "")
        actvSearchFilterDistrict.setBackgroundResource(R.drawable.bg_search_radius_8)
    }

    private fun initListener() = with(binding) {
        ivSearchFilterCloseBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnSearchFilterConfirm.setOnClickListener {
            applyFilters()
        }

        btnSearchFilterReset.setOnClickListener {
            resetFilters()
        }
    }

    private fun resetFilters() = with(binding) {
        filterModel.startDate = null
        filterModel.endDate = null
        filterModel.species = null
        selectedSpecies = null

        selectedCity = null
        selectedDistrict = null

        cityAdapter.updateSelected(null)
        districtAdapter.updateSelected(null)

        tvSearchFilterDateStart.text = getString(R.string.search_filter_date_input_start)
        tvSearchFilterDateStart.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray4
            )
        )
        tvSearchFilterDateEnd.text = getString(R.string.search_filter_date_input_end)
        tvSearchFilterDateEnd.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray4))

        rgSearchSpeciesType.clearCheck()
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.gray6)
        val defaultStyle = R.style.TextAppearance_FindU_Body2_SB_14
        with(rbSearchFilterDog) { setTextAppearance(defaultStyle); setTextColor(defaultColor) }
        with(rbSearchFilterCat) { setTextAppearance(defaultStyle); setTextColor(defaultColor) }
        with(rbSearchFilterEtc) { setTextAppearance(defaultStyle); setTextColor(defaultColor) }

        actvSearchFilterCity.setText("")
        actvSearchFilterDistrict.setText("")
        actvSearchFilterDistrict.isEnabled = false

        flFilterCityContainer.visibility = View.GONE
        flFilterDistrictContainer.visibility = View.GONE
        actvSearchFilterCity.setBackgroundResource(R.drawable.bg_search_radius_8)
        actvSearchFilterDistrict.setBackgroundResource(R.drawable.bg_search_radius_8)

        rotateArrow(ivCityArrow, false)
        rotateArrow(ivDistrictArrow, false)
        rotateArrow(ivBreedArrow, false)

        selectedBreedList.clear()
        updateBreedChipsAndCounter()
        showBreedHintAndClear()
        clearBreedAdapter()
        setBreedFieldEnabled(false)
        setBreedDropdown(false)
    }

    private fun applyFilters() = with(binding) {
        val normalizedSpecies: String? = filterModel.species ?: when (selectedSpecies) {
            "개" -> SpeciesType.DOG.name
            "고양이" -> SpeciesType.CAT.name
            "기타" -> SpeciesType.ETC.name
            else -> null
        }

        val result = SearchFilterUiModel(
            startDate = filterModel.startDate,
            endDate = filterModel.endDate,
            species = normalizedSpecies,
            breeds = if (selectedBreedList.isEmpty()) null
                else selectedBreedList.toList(),
            location = buildLocation()
        )

        val bundle = Bundle().apply {
            putSerializable(SELECTED_FILTER_DATA, result)
        }
        parentFragmentManager.setFragmentResult(FILTER_RESULTS, bundle)
        parentFragmentManager.popBackStack()

    }

    private fun buildLocation(): String? {
        val city = selectedCity?.takeUnless { it.isBlank() || it == "전체" }
        val district = selectedDistrict?.takeUnless { it.isBlank() || it == "전체" }

        return when {
            city == null -> null
            district == null -> city
            else -> "$city $district"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}