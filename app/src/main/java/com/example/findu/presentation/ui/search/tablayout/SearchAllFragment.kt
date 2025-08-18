package com.example.findu.presentation.ui.search.tablayout

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toSearchRvTag
import com.example.findu.databinding.FragmentSearchAllBinding
import com.example.findu.domain.model.search.SearchData
import com.example.findu.presentation.ui.search.BundleTag.FILTER_RESULTS
import com.example.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.example.findu.presentation.ui.search.SearchFilterBottomSheet
import com.example.findu.presentation.ui.search.SearchFragmentDirections
import com.example.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.example.findu.presentation.ui.search.adapter.SearchContentRVAdapter
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
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

    private var lastProtectId = Long.MAX_VALUE
    private var lastReportId = Long.MAX_VALUE

    private var isNewList = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchAllBinding.inflate(inflater, container, false)
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
                lastReportId = searchResults?.firstOrNull()?.lastReportId ?: Long.MAX_VALUE
                lastProtectId = searchResults?.firstOrNull()?.lastProtectId ?: Long.MAX_VALUE
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
        if (isNewList) {
            rvAdapter.submitList(searchList)
            isNewList = false
            binding.rvSearchHorizontalContent.scrollToPosition(0)
            binding.rvSearchHorizontalContent.smoothScrollToPosition(0)

        } else {
            rvAdapter.addData(searchList)
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

    private fun initFilterButton() {
        binding.ibSearchFilter.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_search_to_fragment_search_filter)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.setFragmentResultListener(FILTER_RESULTS, viewLifecycleOwner) { _, bundle ->
            val selected: SearchFilterUiModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(SELECTED_FILTER_DATA, SearchFilterUiModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getSerializable(SELECTED_FILTER_DATA) as? SearchFilterUiModel
            }

            viewModel.updateAllFilterState(selected)

            isNewList = true
            lastReportId = Long.MAX_VALUE
            lastProtectId = Long.MAX_VALUE

            rvAdapter.submitList(emptyList())
            binding.rvSearchHorizontalContent.scrollToPosition(0)

            viewModel.getSearchAllData()
        }
    }

    private fun initRVAdapter() {
        rvAdapter = SearchContentRVAdapter(
            onItemClick = { item ->
                navigateToDetail(item.cardId, item.tag.text, item.name)
            },
            onBookmarkClick = { cardId, isBookmark, tag ->
                viewModel.setInterest(cardId, isBookmark, tag)
            }
        ).apply { submitList(emptyList()) }
        binding.rvSearchHorizontalContent.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rvAdapter
        }

        binding.rvSearchHorizontalContent.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val rvPosition = when (recyclerView.layoutManager) {
                    is LinearLayoutManager -> {
                        (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    }

                    else -> {
                        (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    }
                }

                val totalCount = recyclerView.adapter?.itemCount?.minus(1) ?: 0

                // 페이징 처리
                if (rvPosition == totalCount) {
                    viewModel.getSearchAllData(
                        lastProtectId,
                        lastReportId
                    )
                }
            }
        })
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
            binding.rvSearchHorizontalContent.layoutManager =
                GridLayoutManager(requireContext(), 2)
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
        binding.rvSearchHorizontalContent.adapter = null
        _binding = null
    }
}
