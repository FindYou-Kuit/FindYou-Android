package com.example.findu.presentation.ui.search.filter

import androidx.core.content.ContextCompat
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.search.viewmodel.SearchFilterViewModel

class SearchSpeciesSelector(
    private val binding: FragmentSearchFilterBinding,
    private val viewModel: SearchFilterViewModel
) {
    private var selected: SpeciesType? = null
    private var listener: ((SpeciesType?) -> Unit)? = null

    fun init() = with(binding) {
        val normalColor = ContextCompat.getColor(root.context, R.color.gray6)
        val activeColor = ContextCompat.getColor(root.context, R.color.main_color)
        val normalStyle = R.style.TextAppearance_FindU_Body2_SB_14
        val activeStyle = R.style.TextAppearance_FindU_Body1_SB_16

        fun updateUI(species: SpeciesType?) {
            selected = species

            rbSearchFilterDog.setTextAppearance(normalStyle)
            rbSearchFilterCat.setTextAppearance(normalStyle)
            rbSearchFilterEtc.setTextAppearance(normalStyle)

            rbSearchFilterDog.setTextColor(normalColor)
            rbSearchFilterCat.setTextColor(normalColor)
            rbSearchFilterEtc.setTextColor(normalColor)

            when (species) {
                SpeciesType.DOG -> {
                    rbSearchFilterDog.setTextAppearance(activeStyle)
                    rbSearchFilterDog.setTextColor(activeColor)
                    viewModel.loadBreeds(SpeciesType.DOG)
                }
                SpeciesType.CAT -> {
                    rbSearchFilterCat.setTextAppearance(activeStyle)
                    rbSearchFilterCat.setTextColor(activeColor)
                    viewModel.loadBreeds(SpeciesType.CAT)
                }
                SpeciesType.ETC -> {
                    rbSearchFilterEtc.setTextAppearance(activeStyle)
                    rbSearchFilterEtc.setTextColor(activeColor)
                    viewModel.loadBreeds(SpeciesType.ETC)
                }
                null -> {}
            }

            listener?.invoke(species)
        }

        rgSearchSpeciesType.setOnCheckedChangeListener { _, id ->
            val type = when (id) {
                R.id.rb_search_filter_dog -> SpeciesType.DOG
                R.id.rb_search_filter_cat -> SpeciesType.CAT
                R.id.rb_search_filter_etc -> SpeciesType.ETC
                else -> null
            }
            updateUI(type)
        }
    }

    fun setOnSpeciesChangedListener(onChanged: (SpeciesType?) -> Unit) {
        listener = onChanged
    }

    fun getSelected(): String? = selected?.name

    fun reset() {
        selected = null
        binding.rgSearchSpeciesType.clearCheck()

        val normalColor = ContextCompat.getColor(binding.root.context, R.color.gray6)
        val normalStyle = R.style.TextAppearance_FindU_Body2_SB_14
        with(binding) {
            rbSearchFilterDog.setTextAppearance(normalStyle)
            rbSearchFilterDog.setTextColor(normalColor)
            rbSearchFilterCat.setTextAppearance(normalStyle)
            rbSearchFilterCat.setTextColor(normalColor)
            rbSearchFilterEtc.setTextAppearance(normalStyle)
            rbSearchFilterEtc.setTextColor(normalColor)
        }

        listener?.invoke(null)
    }
}