package com.example.findu.presentation.ui.search.tablayout

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
import com.example.findu.databinding.FragmentSearchRescueBinding
import com.example.findu.domain.model.search.SearchData
import com.example.findu.presentation.ui.search.SearchDisappearDetailFragment
import com.example.findu.presentation.ui.search.SearchFilterBottomSheet
import com.example.findu.presentation.ui.search.SearchProtectingDetailFragment
import com.example.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.example.findu.presentation.ui.search.SearchWitnessDetailFragment
import com.example.findu.presentation.ui.search.adapter.SearchContentRVAdapter
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchRescueFragment : Fragment() {

    private var _binding: FragmentSearchRescueBinding? = null
    private val binding get() = _binding!!

    private var items = ArrayList<SearchRv>()
    private lateinit var rvAdapter: SearchContentRVAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchRescueBinding.inflate(layoutInflater)
        initRVAdapter()
        observeViewModel()
        viewModel.getSearchProtectData()
        initToggleButton()
        initFilterButton()
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener("filterResults", this) { _, bundle ->
            val selectedFilters =
                bundle.getStringArrayList("selectedFilters") ?: return@setFragmentResultListener

            binding.cgSearchRescueGroupFilters.removeAllViews()
            updateFilterChips(selectedFilters)

        }
        val chipGroup = binding.cgSearchRescueGroupFilters
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as? Chip
            chip?.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
            }
        }
    }

    private fun updateFilterChips(filters: List<String>?) {
        val chipGroup = binding.cgSearchRescueGroupFilters
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
        binding.ibSearchRescueFilter.setOnClickListener {
            val bottomSheet = SearchFilterBottomSheet()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun initRVAdapter() {
        rvAdapter = SearchContentRVAdapter(items) { item ->
            navigateToDetail(item.cardId, item.tag.text, item.name)
        }
        binding.rvSearchRescueHorizontalContent.adapter = rvAdapter
        binding.rvSearchRescueHorizontalContent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun openDetailFragment(selectedItem: SearchRv) {
        val detailFragment = SearchProtectingDetailFragment().apply {
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
        binding.ibSearchRescueHorizontalSort.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            while (binding.rvSearchRescueHorizontalContent.itemDecorationCount > 0) {
                binding.rvSearchRescueHorizontalContent.removeItemDecorationAt(0)
            }

            binding.rvSearchRescueHorizontalContent.addItemDecoration(SearchSpacingItemDecoration(10))
            binding.rvSearchRescueHorizontalContent.layoutManager =
                GridLayoutManager(requireContext(), 2)
            rvAdapter.setGridMode(true)
            binding.ibSearchRescueHorizontalSort.setImageResource(R.drawable.ic_search_grid_sort)

        } else {
            binding.rvSearchRescueHorizontalContent.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAdapter.setGridMode(false)
            binding.ibSearchRescueHorizontalSort.setImageResource(R.drawable.ic_search_horizontal_sort)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.cgSearchRescueGroupFilters.removeAllViews()
        _binding = null
    }

}
