package com.example.findu.presentation.ui.search.tablayout

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toSearchRvTag
import com.example.findu.databinding.FragmentSearchAllBinding
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.search.SearchFragmentDirections
import com.example.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.example.findu.presentation.ui.search.adapter.SearchListAdapter
import com.example.findu.presentation.ui.search.model.DummyProvider
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAllFragment : Fragment() {

    private var _binding: FragmentSearchAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: SearchListAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()

    private var lastProtectId = Long.MAX_VALUE
    private var lastReportId = Long.MAX_VALUE

    private var isNewList = false
    private var items = ArrayList<SearchAnimal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchAllBinding.inflate(inflater, container, false)
        initRVAdapter()
        initDummyItems()
        setupRV(items)

        return binding.root
    }

    @SuppressLint("VisibleForTests")
    private fun initDummyItems() {
        items.addAll(DummyProvider.getDummyAnimals())
        items.addAll(DummyProvider.getDummyAnimals())
        items.addAll(DummyProvider.getDummyAnimals())
    }

    private fun setupRV(searchDataList: List<SearchAnimal>) {
        val searchList = searchDataList.map { item ->
            SearchRv(
                image = item.thumbnailImageUrl,
                name = item.title,
                date = item.date,
                address = item.location,
                isBookmark = item.interest,
                tag = item.tag.toSearchRvTag(),
                cardId = item.cardId
            )
        }

        if (isNewList) {
            listAdapter.submitContent(searchList)
            isNewList = false
            binding.rvSearchAll.scrollToPosition(0)
            binding.rvSearchAll.smoothScrollToPosition(0)
        } else {
            listAdapter.addContent(searchList)
        }
    }

    private fun navigateToDetail(cardId: Long, tag: String, name: String) {
        when (tag) {
            "보호중" ->
                findNavController().navigate(
                    SearchFragmentDirections.actionFragmentSearchToFragmentSearchDetailProtecting(
                        id = cardId.toString(),
                        tag = tag,
                        name = name
                    )
                )

            "목격신고" -> findNavController().navigate(
                SearchFragmentDirections.actionFragmentSearchToFragmentSearchDetailWitness(
                    id = cardId.toString(),
                    tag = tag,
                    name = name
                )
            )

            "실종신고" -> findNavController().navigate(
                SearchFragmentDirections.actionFragmentSearchToFragmentSearchDetailDisappear(
                    id = cardId.toString(),
                    tag = tag,
                    name = name
                )
            )
        }
    }

    private fun navigateToFilter() {
        findNavController().navigate(R.id.action_fragment_search_to_fragment_search_filter)
    }

    private fun initRVAdapter() {
        listAdapter = SearchListAdapter(
            onFilterClick = { navigateToFilter() },
            onToggleClick = { toggleLayoutMode() },
            onItemClick = { item -> navigateToDetail(item.cardId, item.tag.text, item.name) },
            onBookmarkClick = { cardId, isBookmark, tag ->
                viewModel.setInterest(cardId, isBookmark, tag)
            }
        )

        binding.rvSearchAll.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }

        binding.rvSearchAll.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager
                val lastPos = when (lm) {
                    is LinearLayoutManager -> lm.findLastVisibleItemPosition()
                    is GridLayoutManager -> lm.findLastVisibleItemPosition()
                    else -> return
                }
                val total = (recyclerView.adapter?.itemCount ?: 1) - 1
                if (lastPos == total) {
                    viewModel.getSearchAllData(lastProtectId, lastReportId)
                }
            }
        })
    }

    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            while (binding.rvSearchAll.itemDecorationCount > 0) {
                binding.rvSearchAll.removeItemDecorationAt(0)
            }
            binding.rvSearchAll.addItemDecoration(SearchSpacingItemDecoration(10))

            val grid = GridLayoutManager(requireContext(), 2)

            grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (position == 0) 2 else 1
            }

            binding.rvSearchAll.layoutManager = grid
            listAdapter.setGridMode(true)
        } else {
            binding.rvSearchAll.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listAdapter.setGridMode(false)
        }
    }

    companion object {
        const val HEADER_VIEW_TYPE = 1000
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSearchAll.adapter = null
        _binding = null
    }
}