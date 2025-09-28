package com.example.findu.presentation.ui.search.tablayout

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toSearchRvTag
import com.example.findu.databinding.FragmentSearchReportBinding
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.search.BundleTag.FILTER_RESULTS
import com.example.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.example.findu.presentation.ui.search.SearchFragmentDirections
import com.example.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.example.findu.presentation.ui.search.adapter.SearchListAdapter
import com.example.findu.presentation.ui.search.model.DummyProvider
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchReportFragment : Fragment() {

    private var _binding: FragmentSearchReportBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: SearchListAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()

    private var lastReportId = Long.MAX_VALUE
    private var isNewList = false

    private var items = ArrayList<SearchAnimal>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchReportBinding.inflate(inflater, container, false)
        initRVAdapter()
//        observeViewModel()
//        viewModel.getSearchReportData()
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.setFragmentResultListener(
            FILTER_RESULTS,
            viewLifecycleOwner
        ) { _, bundle ->
            val selected: SearchFilterUiModel? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getSerializable(SELECTED_FILTER_DATA, SearchFilterUiModel::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    bundle.getSerializable(SELECTED_FILTER_DATA) as? SearchFilterUiModel
                }

            viewModel.updateReportFilterState(selected)

            isNewList = true
            lastReportId = Long.MAX_VALUE

            listAdapter.submitList(emptyList())
            binding.rvSearchReport.scrollToPosition(0)

            viewModel.getSearchReportData()
        }
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
            binding.rvSearchReport.scrollToPosition(0)
            binding.rvSearchReport.smoothScrollToPosition(0)
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
            onBookmarkClick = { cardId, isBookmark, tag -> viewModel.setInterest(cardId, isBookmark, tag) }
        )

        binding.rvSearchReport.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            itemAnimator = null
        }

        binding.rvSearchReport.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastPos = when (val manager = recyclerView.layoutManager) {
                    is LinearLayoutManager -> manager.findLastVisibleItemPosition()
                    else -> return
                }
                val total = (recyclerView.adapter?.itemCount ?: 1) - 1
                //페이징 처리
                if (lastPos == total) {
                    viewModel.getSearchReportData(lastReportId)
                }
            }
        })
    }

    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            while (binding.rvSearchReport.itemDecorationCount > 0) {
                binding.rvSearchReport.removeItemDecorationAt(0)
            }
            binding.rvSearchReport.addItemDecoration(SearchSpacingItemDecoration(10))

            val grid = GridLayoutManager(requireContext(), 2)

            grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (position == 0) 2 else 1
            }

            binding.rvSearchReport.layoutManager = grid
            listAdapter.setGridMode(true)
        } else {
            binding.rvSearchReport.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listAdapter.setGridMode(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSearchReport.adapter = null
        _binding = null
    }

}
