package com.example.findu.presentation.ui.search.tablayout

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.data.mapper.toDomain.toSearchRvTag
import com.example.findu.databinding.FragmentSearchAllBinding
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.example.findu.presentation.ui.search.SearchFragmentDirections
import com.example.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.example.findu.presentation.ui.search.adapter.SearchListAdapter
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.model.SearchType
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchAllFragment : Fragment() {

    private var _binding: FragmentSearchAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: SearchListAdapter
    private var isGridMode = false
    private val viewModel by activityViewModels<SearchViewModel>()

    private var lastId = Long.MAX_VALUE

    private var isNewList = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchAllBinding.inflate(inflater, container, false)
        initRVAdapter()
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFilterResult()
        viewModel.getSearchData(SearchType.ALL, lastId)
    }

    private fun observeFilterResult() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<SearchFilterUiModel>(SELECTED_FILTER_DATA)
            ?.observe(viewLifecycleOwner) { selected ->
                Log.d("SearchFilter", "필터 수신됨: $selected")

                viewModel.updateAllFilterState(selected)
                isNewList = true
                lastId = Long.MAX_VALUE

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(50)
                    viewModel.getSearchData(SearchType.ALL, lastId)
                }

                findNavController().currentBackStackEntry
                    ?.savedStateHandle
                    ?.remove<SearchFilterUiModel>(SELECTED_FILTER_DATA)
            }
    }


    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allSearchData.collectLatest { searchResults ->
                if (!searchResults.isNullOrEmpty()) {
                    val animals = searchResults.flatMap { it.cards }
                    setupRV(animals)
                    lastId = searchResults.last().lastId
                } else {
                    setupRV(emptyList())
                    lastId = Long.MAX_VALUE
                }
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

    private fun setupRV(searchDataList: List<SearchAnimal>) {
        val searchList = searchDataList.map { item ->
            SearchRv(
                image = item.thumbnailImageUrl ?: "",
                name = item.title,
                date = item.date,
                location = item.location ?: "",
                isBookmark = item.interest,
                tag = item.tag.toSearchRvTag(),
                reportId = item.reportId
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
            onItemClick = { item -> navigateToDetail(item.reportId, item.tag.text, item.name) },
            onBookmarkClick = { cardId, isBookmark, tag ->
                viewModel.setInterest(cardId, isBookmark, tag)
            }
        )

        binding.rvSearchAll.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
                    viewModel.getSearchData(SearchType.ALL, lastId)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSearchAll.adapter = null
        _binding = null
    }
}