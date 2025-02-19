package com.example.findu.presentation.ui.search.tablayout

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toSearchRvTag
import com.example.findu.databinding.FragmentSearchAllBinding
import com.example.findu.domain.model.search.SearchData
import com.example.findu.presentation.ui.search.BundleTag.FILTER_RESULTS
import com.example.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.example.findu.presentation.ui.search.SearchDisappearDetailFragment
import com.example.findu.presentation.ui.search.SearchFilterBottomSheet
import com.example.findu.presentation.ui.search.SearchProtectingDetailFragment
import com.example.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.example.findu.presentation.ui.search.SearchWitnessDetailFragment
import com.example.findu.presentation.ui.search.adapter.SearchContentRVAdapter
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchAllFragment : Fragment() {

    private var _binding: FragmentSearchAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: SearchContentRVAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchAllBinding.inflate(layoutInflater)
        initRVAdapter()
        observeViewModel()
        viewModel.getSearchAllData()
        initToggleButton()
        initFilterButton()
        return binding.root
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allSearchData.collectLatest { searchResults ->
                setupRV(searchResults ?: emptyList())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.collectLatest { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRV(searchDataList: List<SearchData>) {
        val searchList = searchDataList.flatMap { data ->
            data.cards.map {
                SearchRv(
                    image = it.thumbnailImageUrl,
                    name = it.title,
                    date = it.date,
                    address = it.location,
                    isBookmark = it.interest,
                    tag = it.tag.toSearchRvTag(),
                    cardId = it.cardId
                )
            }
        }
        rvAdapter.updateData(searchList)
        binding.rvSearchAllHorizontalContent.scrollToPosition(0)
    }

    private fun navigateToDetail(cardId: Long, tag: String, name: String) {
        val fragment = when (tag) {
            "보호중" -> SearchProtectingDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("cardId", cardId)
                    putString("tag", tag)
                    putString("name", name)
                }
            }

            "목격신고" -> SearchWitnessDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("cardId", cardId)
                    putString("tag", tag)
                    putString("name", name)
                }
            }

            "실종신고" -> SearchDisappearDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("cardId", cardId)
                    putString("tag", tag)
                    putString("name", name)
                }
            }

            else -> return
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initFilterButton() {
        binding.ibSearchAllFilter.setOnClickListener {
            val bottomSheet = SearchFilterBottomSheet()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener(FILTER_RESULTS, this) { _, bundle ->

            binding.cgSearchAllGroupFilters.removeAllViews()

            val filterUiModel: SearchFilterUiModel? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getSerializable(SELECTED_FILTER_DATA, SearchFilterUiModel::class.java)
                } else {
                    bundle.getSerializable(SELECTED_FILTER_DATA) as? SearchFilterUiModel
                }
            updateFilterChips(filterUiModel)

        }

        binding.rvSearchAllHorizontalContent.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                when {
                    firstVisibleItemPosition == 0 -> {
                        binding.hsSearchAllFilters.elevation = 0f
                    }

                    rvAdapter.returnItemSize() == 0 -> {
                        binding.hsSearchAllFilters.elevation = 0f
                    }

                    else -> {
                        binding.hsSearchAllFilters.elevation = 8f
                    }
                }
            }
        })


    }

    private fun updateFilterChips(filters: SearchFilterUiModel?) {
        val chipGroup = binding.cgSearchAllGroupFilters
        chipGroup.removeAllViews()
        viewModel.updateAllFilterState(filters)

        if (filters == null) return

        filters.species?.let { species ->
            if (species.isEmpty()) return
            val chip =
                layoutInflater.inflate(R.layout.item_search_filter_chip, chipGroup, false) as Chip

            chip.text = if (species == "개") "강아지" else species
            chip.setOnCloseIconClickListener {
                chipGroup.removeAllViews()
                viewModel.updateAllFilterState(
                    viewModel.allFilter?.copy(
                        species = null,
                        breeds = null
                    )
                )
                viewModel.allFilter?.location?.let {
                    val locationChip =
                        layoutInflater.inflate(
                            R.layout.item_search_filter_chip,
                            chipGroup,
                            false
                        ) as Chip
                    locationChip.text = it
                    chipGroup.addView(locationChip)
                }
            }
            chipGroup.addView(chip)
        }

        filters.breeds?.forEach { breed ->
            val chip =
                layoutInflater.inflate(R.layout.item_search_filter_chip, chipGroup, false) as Chip
            chip.text = breed
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
                viewModel.updateAllFilterState(
                    viewModel.allFilter?.copy(
                        breeds = viewModel.allFilter?.breeds?.filter { it != breed }
                    )
                )
            }
            chipGroup.addView(chip)
        }

        filters.location?.let { location ->
            if (location.isEmpty()) return
            val chip =
                layoutInflater.inflate(R.layout.item_search_filter_chip, chipGroup, false) as Chip
            chip.text = location
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
                viewModel.updateAllFilterState(
                    viewModel.reportFilter?.copy(location = null)
                )
            }
            chipGroup.addView(chip)
        }

    }

    private fun initRVAdapter() {
        rvAdapter = SearchContentRVAdapter(emptyList()) { item ->
            navigateToDetail(item.cardId, item.tag.text, item.name)
        }
        binding.rvSearchAllHorizontalContent.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rvAdapter
        }
    }

    private fun initToggleButton() {
        binding.ibSearchAllHorizontalSort.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            while (binding.rvSearchAllHorizontalContent.itemDecorationCount > 0) {
                binding.rvSearchAllHorizontalContent.removeItemDecorationAt(0)
            }

            binding.rvSearchAllHorizontalContent.addItemDecoration(SearchSpacingItemDecoration(10))
            binding.rvSearchAllHorizontalContent.layoutManager =
                GridLayoutManager(requireContext(), 2)
            rvAdapter.setGridMode(true)
            binding.ibSearchAllHorizontalSort.setImageResource(R.drawable.ic_search_grid_sort)
        } else {
            binding.rvSearchAllHorizontalContent.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAdapter.setGridMode(false)
            binding.ibSearchAllHorizontalSort.setImageResource(R.drawable.ic_search_horizontal_sort)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.cgSearchAllGroupFilters.removeAllViews()
        _binding = null
    }
}
