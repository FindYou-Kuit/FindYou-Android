package com.example.findu.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchRescueBinding
import com.example.findu.presentation.ui.search.model.SearchData
import com.example.findu.presentation.ui.search.adapter.SearchContentRVAdapter
import com.example.findu.presentation.ui.search.model.SearchStatus
import com.google.android.material.chip.Chip

class SearchRescueFragment : Fragment() {

    private lateinit var binding: FragmentSearchRescueBinding
    private var items = ArrayList<SearchData>()
    private lateinit var rvAdapter: SearchContentRVAdapter
    private var isGridMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchRescueBinding.inflate(layoutInflater)
        initDummyItems()
        initRVAdapter()
        initToggleButton()
        setupFilterButton()
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
    override fun onDestroyView() {
        super.onDestroyView()
        binding.cgSearchGroupFilters.removeAllViews()
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
    private fun setupFilterButton() {
        binding.ibSearchFilter.setOnClickListener {
            val bottomSheet = SearchFilterBottomSheet()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun initDummyItems() {
        items.addAll(
            arrayListOf(
                SearchData(
                    name = "말티즈",
                    image = R.drawable.img_search_content,
                    date = "2024-11-23",
                    address = "성신구 내동 628-1",
                    isBookmark = true,
                    status = SearchStatus.PROTECTING
                ),
                SearchData(
                    name = "믹스견",
                    image = R.drawable.img_search_content,
                    date = "2024-11-24",
                    address = "성신구 내동 628-1",
                    isBookmark = false,
                    status = SearchStatus.PROTECTING
                ),
                SearchData(
                    name = "웰시코기",
                    image = R.drawable.img_search_content,
                    date = "2024-11-25",
                    address = "성신구 내동 628-1",
                    isBookmark = false,
                    status = SearchStatus.PROTECTING
                ),
                SearchData(
                    name = "믹스견",
                    image = R.drawable.img_search_content,
                    date = "2024-11-25",
                    address = "성신구 내동 628-1",
                    isBookmark = false,
                    status = SearchStatus.PROTECTING
                )
            )
        )
    }

    private fun initRVAdapter() {
        rvAdapter = SearchContentRVAdapter(items) { item ->
            openDetailFragment(item)
        }
        binding.rvSearchHorizontalContent.adapter = rvAdapter
        binding.rvSearchHorizontalContent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
    private fun openDetailFragment(selectedItem: SearchData) {
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
}
