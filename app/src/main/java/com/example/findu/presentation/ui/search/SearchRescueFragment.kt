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
        return binding.root
    }

    private fun initDummyItems() {
        items.addAll(
            arrayListOf(
                SearchData(
                    name = "말티즈",
                    image = R.drawable.img_search_content,
                    date = "2024-11-23",
                    address = "성신구 내동 628-1",
                    isBookmark = true
                ),
                SearchData(
                    name = "믹스견",
                    image = R.drawable.img_search_content,
                    date = "2024-11-24",
                    address = "성신구 내동 628-1",
                    isBookmark = false
                ),
                SearchData(
                    name = "웰시코기",
                    image = R.drawable.img_search_content,
                    date = "2024-11-25",
                    address = "성신구 내동 628-1",
                    isBookmark = false
                ),
                SearchData(
                    name = "믹스견",
                    image = R.drawable.img_search_content,
                    date = "2024-11-25",
                    address = "성신구 내동 628-1",
                    isBookmark = false
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

            binding.rvSearchHorizontalContent.addItemDecoration(SpacingItemDecoration(10))
            binding.rvSearchHorizontalContent.layoutManager = GridLayoutManager(requireContext(), 2)
            rvAdapter.setGridMode(true)
        } else {
            binding.rvSearchHorizontalContent.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAdapter.setGridMode(false)
        }
    }
}
