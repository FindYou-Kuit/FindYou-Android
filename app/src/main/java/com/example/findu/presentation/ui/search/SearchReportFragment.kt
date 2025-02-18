package com.example.findu.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toSearchRvTag
import com.example.findu.databinding.FragmentSearchReportBinding
import com.example.findu.domain.model.search.SearchData
import com.example.findu.presentation.ui.search.adapter.SearchContentRVAdapter
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchReportFragment : Fragment() {

    private lateinit var binding: FragmentSearchReportBinding
    private var items = ArrayList<SearchRv>()
    private lateinit var rvAdapter: SearchContentRVAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchReportBinding.inflate(layoutInflater)
        initRVAdapter()
        observeViewModel()
        viewModel.getSearchReportData()
        initFilterButton()
        initToggleButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener("filterResults", this) { _, bundle ->
            val selectedFilters =
                bundle.getStringArrayList("selectedFilters") ?: return@setFragmentResultListener
            binding.cgSearchGroupFilters.removeAllViews()

            updateFilterChips(selectedFilters)

        }
        val chipGroup = binding.cgSearchGroupFilters
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as? Chip
            chip?.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
            }
        }
    }


    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchData.collectLatest { searchResults ->
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
    }

    private fun navigateToDetail(cardId: Long, tag: String) {
        val fragment = when (tag) {
            "목격신고" -> SearchWitnessDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("report_Id", cardId)
                }
            }
            "실종신고" -> SearchDisappearDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("report_Id", cardId)
                }
            }
            else -> return
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun updateFilterChips(filters: List<String>?) {
        val chipGroup = binding.cgSearchGroupFilters
        chipGroup.removeAllViews()

        if (filters.isNullOrEmpty() || filters.all { it.isBlank() }) {
            return
        }
        filters.forEach { filterText ->
            val chip =
                layoutInflater.inflate(R.layout.item_search_filter_chip, chipGroup, false) as Chip
            chip.text = filterText
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
            }
            chipGroup.addView(chip)
        }
    }

    private fun initFilterButton() {
        binding.ibSearchFilter.setOnClickListener {
            val bottomSheet = SearchFilterBottomSheet()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun initRVAdapter() {
        rvAdapter = SearchContentRVAdapter(items) { item ->
            navigateToDetail(item.cardId, item.tag.text)
        }
        binding.rvSearchHorizontalContent.adapter = rvAdapter
        binding.rvSearchHorizontalContent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun openDetailFragment(selectedItem: SearchRv) {
        val detailFragment = SearchWitnessDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("selectedItem", selectedItem)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initToggleButton() {
        binding.ibSearchHorizontalSort.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            while (binding.rvSearchHorizontalContent.itemDecorationCount > 0) {
                binding.rvSearchHorizontalContent.removeItemDecorationAt(0)
            }

            binding.rvSearchHorizontalContent.addItemDecoration(SearchSpacingItemDecoration(10))
            binding.rvSearchHorizontalContent.layoutManager = GridLayoutManager(requireContext(), 2)
            rvAdapter.setGridMode(true)
            binding.ibSearchHorizontalSort.setImageResource(R.drawable.ic_search_grid_sort)

        } else {
            binding.rvSearchHorizontalContent.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAdapter.setGridMode(false)
            binding.ibSearchHorizontalSort.setImageResource(R.drawable.ic_search_horizontal_sort)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.cgSearchGroupFilters.removeAllViews()
    }

}
