package com.example.findu.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.search.dialog.SearchFilterDateDialog
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

    }

    private fun initListener() = with(binding) {
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

        rgSearchSpeciesType.setOnCheckedChangeListener { _, checkedId ->
            val defaultColor = ContextCompat.getColor(requireContext(), R.color.gray4)
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