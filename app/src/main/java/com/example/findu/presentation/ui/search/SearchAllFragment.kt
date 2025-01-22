package com.example.findu.presentation.ui.search

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private fun openDetailFragment(selectedItem: SearchData) {
        val detailFragment = SearchDisappearDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("selectedItem", selectedItem)
            }
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, detailFragment)
            .addToBackStack(null)
            .commit()
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
                    image = R.drawable.img_search_content_mix_dog,
                    date = "2024-11-24",
                    address = "성신구 내동 628-1",
                    isBookmark = false
                ),
                SearchData(
                    name = "웰시코기",
                    image = R.drawable.img_search_content_welshicorgi,
                    date = "2024-11-25",
                    address = "성신구 내동 628-1",
                    isBookmark = false
                ),
                SearchData(
                    name = "믹스견",
                    image = R.drawable.img_search_content_mix_dog2,
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

class SpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
            val index = layoutParams.spanIndex
            if (index == 0) {
                outRect.right = spacing / 2
            } else {
                outRect.left = spacing / 2
            }
        } else {
            outRect.top = spacing / 2
            outRect.bottom = spacing / 2
        }
    }
}
