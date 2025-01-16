package com.example.findu.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchAllBinding
import com.example.findu.presentation.ui.search.model.SearchData
import com.example.findu.presentation.ui.search.adapter.SearchContentRVAdapter

class SearchAllFragment : Fragment() {

    private lateinit var binding: FragmentSearchAllBinding
    private var items = ArrayList<SearchData>()
    private lateinit var rvAdapter: SearchContentRVAdapter
    private var isGridMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchAllBinding.inflate(layoutInflater)
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
                    address = "성신구 내동 628-1"
                ),
                SearchData(
                    name = "푸들",
                    image = R.drawable.img_search_content,
                    date = "2024-11-24",
                    address = "서울 강남구 역삼동 123-4"
                ),
                SearchData(
                    name = "치와와",
                    image = R.drawable.img_search_content,
                    date = "2024-11-25",
                    address = "대구 동구 신천동 456-7"
                )
            )
        )
    }

    private fun initRVAdapter() {
        rvAdapter = SearchContentRVAdapter(items)
        binding.rvSearchHorizontalContent.adapter = rvAdapter
        binding.rvSearchHorizontalContent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun initToggleButton() {
        binding.ibSearchHorizontalSort.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            binding.rvSearchHorizontalContent.layoutManager = GridLayoutManager(requireContext(), 2)
            rvAdapter.setGridMode(true)
        } else {
            binding.rvSearchHorizontalContent.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAdapter.setGridMode(false)
        }
    }
}