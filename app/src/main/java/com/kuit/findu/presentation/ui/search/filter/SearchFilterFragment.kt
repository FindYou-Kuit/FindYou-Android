package com.kuit.findu.presentation.ui.search.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kuit.findu.databinding.FragmentSearchFilterBinding
import com.kuit.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.kuit.findu.presentation.ui.search.model.SearchFilterUiModel
import com.kuit.findu.presentation.ui.search.model.SearchType
import com.kuit.findu.presentation.ui.search.viewmodel.SearchFilterViewModel
import com.kuit.findu.presentation.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFilterFragment : Fragment() {

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<SearchFilterFragmentArgs>()

    private val filterViewModel: SearchFilterViewModel by viewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val filterModel = SearchFilterUiModel()

    private lateinit var speciesSelector: SearchSpeciesSelector
    private lateinit var breedSelector: SearchBreedSelector
    private lateinit var locationSelector: SearchLocationSelector
    private lateinit var calendarSelector: SearchCalendarSelector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        speciesSelector = SearchSpeciesSelector(binding, filterViewModel)
        breedSelector = SearchBreedSelector(binding)
        locationSelector = SearchLocationSelector(binding)
        calendarSelector = SearchCalendarSelector(binding, filterModel, parentFragmentManager)

        speciesSelector.init()
        breedSelector.init()
        locationSelector.init(filterViewModel, viewLifecycleOwner)
        calendarSelector.init()

        connectSpeciesAndBreed()
        initListeners()
    }

    private fun connectSpeciesAndBreed() {
        speciesSelector.setOnSpeciesChangedListener { species ->
            if (species == null) {
                breedSelector.reset()
                breedSelector.setBreedFieldEnabled(false)
                return@setOnSpeciesChangedListener
            }

            breedSelector.reset()
            breedSelector.setBreedFieldEnabled(true)
            filterViewModel.loadBreeds(species)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            filterViewModel.breedList.collectLatest { breeds ->
                if (breeds.isNotEmpty()) {
                    breedSelector.setBreeds(breeds)
                } else {
                    breedSelector.reset()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            filterViewModel.errorMessage.collectLatest { msg ->
                msg?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            }
        }
    }


    private fun initListeners() = with(binding) {
        ivSearchFilterCloseBtn.setOnClickListener { findNavController().popBackStack() }
        btnSearchFilterReset.setOnClickListener { resetFilters() }
        btnSearchFilterConfirm.setOnClickListener { applyFilters() }
    }

    private fun resetFilters() {
        filterModel.startDate = null
        filterModel.endDate = null

        speciesSelector.reset()
        breedSelector.reset()
        locationSelector.reset()
        calendarSelector.reset()

        when (args.searchType) {
            SearchType.ALL -> {
                searchViewModel.clearFilterState(SearchType.ALL)
                searchViewModel.getSearchData(SearchType.ALL)
            }
            SearchType.REPORTING -> {
                searchViewModel.clearFilterState(SearchType.REPORTING)
                searchViewModel.getSearchData(SearchType.REPORTING)
            }
            SearchType.PROTECTING -> {
                searchViewModel.clearFilterState(SearchType.PROTECTING)
                searchViewModel.getSearchData(SearchType.PROTECTING)
            }
        }
    }

    private fun applyFilters() {
        val s = filterModel.startDate
        val e = filterModel.endDate
        val (startDate, endDate) = when {
            s == null && e == null -> null to null
            s != null && e == null -> s to s
            s == null && e != null -> e to e
            else -> s to e
        }

        val species = speciesSelector.getSelected()
        val breeds = breedSelector.getSelected()
        val location = locationSelector.getSelected()

        val isAllEmpty = listOfNotNull(startDate, endDate, species, location)
            .isEmpty() && breeds.isNullOrEmpty()

        val result = if (isAllEmpty) {
            SearchFilterUiModel()
        } else {
            SearchFilterUiModel(
                startDate = startDate,
                endDate = endDate,
                species = species,
                breeds = breeds,
                location = location
            )
        }

        when (args.searchType) {
            SearchType.ALL -> searchViewModel.updateAllFilterState(result)
            SearchType.REPORTING -> searchViewModel.updateReportFilterState(result)
            SearchType.PROTECTING -> searchViewModel.updateProtectFilterState(result)
        }

        findNavController().previousBackStackEntry
            ?.savedStateHandle
            ?.set(SELECTED_FILTER_DATA, result)
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}