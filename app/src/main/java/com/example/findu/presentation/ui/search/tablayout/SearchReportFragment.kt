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
import com.example.findu.databinding.FragmentSearchReportBinding
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
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchReportFragment : Fragment() {

    private var _binding: FragmentSearchReportBinding? = null
    private val binding get() = _binding!!
    private var items = ArrayList<SearchRv>()
    private lateinit var rvAdapter: SearchContentRVAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()

    private var lastReportId = Long.MAX_VALUE
    private var isNewList = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchReportBinding.inflate(layoutInflater)
        initRVAdapter()
        observeViewModel()
        viewModel.getSearchReportData()
        initFilterButton()
        initToggleButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener(FILTER_RESULTS, this) { _, bundle ->

            binding.cgSearchReportGroupFilters.removeAllViews()
            val filterUiModel: SearchFilterUiModel? =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getSerializable(SELECTED_FILTER_DATA, SearchFilterUiModel::class.java)
                } else {
                    bundle.getSerializable(SELECTED_FILTER_DATA) as? SearchFilterUiModel
                }
            isNewList = true
            updateFilterChips(filterUiModel)
        }

        binding.rvSearchReportHorizontalContent.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                when {
                    firstVisibleItemPosition == 0 -> {
                        binding.hsSearchReportFilters.elevation = 0f
                    }

                    rvAdapter.itemCount == 0 -> {
                        binding.hsSearchReportFilters.elevation = 0f
                    }

                    else -> {
                        binding.hsSearchReportFilters.elevation = 8f
                    }
                }
            }
        })
    }


    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reportSearchData.collectLatest { searchResults ->
                setupRV(searchResults ?: emptyList())
                lastReportId = searchResults?.firstOrNull()?.lastReportId ?: Long.MAX_VALUE
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
                    reportId = it.reportId
                )
            }
        }
        if (isNewList) {
            rvAdapter.submitList(searchList)
            isNewList = false
            binding.rvSearchReportHorizontalContent.scrollToPosition(0)
            binding.rvSearchReportHorizontalContent.smoothScrollToPosition(0)
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


    private fun updateFilterChips(filters: SearchFilterUiModel?) {
        val chipGroup = binding.cgSearchReportGroupFilters
        chipGroup.removeAllViews()

        if (filters == null) return

        viewModel.updateReportFilterState(filters)
        isNewList = true

        filters.species?.let { species ->
            if (species.isEmpty()) return
            val chip =
                layoutInflater.inflate(R.layout.item_search_filter_chip, chipGroup, false) as Chip

            chip.text = if (species == "개") "강아지" else species
            chip.setOnCloseIconClickListener {
                isNewList = true
                chipGroup.removeAllViews()
                viewModel.updateReportFilterState(
                    viewModel.reportFilter?.copy(
                        species = null,
                        breeds = null
                    )
                )
                viewModel.reportFilter?.location?.let {
                    if (it.isNotBlank()) {
                        val locationChip =
                            layoutInflater.inflate(
                                R.layout.item_search_filter_chip,
                                chipGroup,
                                false
                            ) as Chip
                        locationChip.text = it
                        locationChip.setOnCloseIconClickListener {
                            isNewList = true
                            chipGroup.removeView(chip)
                            viewModel.updateReportFilterState(
                                viewModel.reportFilter?.copy(location = null)
                            )
                        }
                        chipGroup.addView(locationChip)
                    }
                }
            }
            chipGroup.addView(chip)
        }

        filters.breeds?.forEach { breed ->
            val chip =
                layoutInflater.inflate(R.layout.item_search_filter_chip, chipGroup, false) as Chip
            chip.text = breed
            chip.setOnCloseIconClickListener {
                isNewList = true
                chipGroup.removeView(chip)
                viewModel.updateReportFilterState(
                    viewModel.reportFilter?.copy(breeds = viewModel.reportFilter?.breeds?.filter { it != breed })
                )
            }
            chipGroup.addView(chip)
        }

        filters.location?.let { location ->
            if (location.isEmpty()) return
            val chip =
                layoutInflater.inflate(R.layout.item_search_filter_chip, chipGroup, false) as Chip
            chip.text = location
            chip.setOnCloseIconClickListener {
                isNewList = true
                chipGroup.removeView(chip)
                viewModel.updateReportFilterState(
                    viewModel.reportFilter?.copy(location = null)
                )
            }
            chipGroup.addView(chip)
        }
    }

    private fun initFilterButton() {
        binding.ibSearchReportFilter.setOnClickListener {
            val bottomSheet = SearchFilterBottomSheet()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun initRVAdapter() {
        rvAdapter = SearchContentRVAdapter(
            onItemClick = { item ->
                navigateToDetail(item.reportId, item.tag.text, item.name)
            },
            onBookmarkClick = { cardId, isBookmark, tag ->
                viewModel.setInterest(cardId, isBookmark, tag)
            }
        ).apply { submitList(emptyList()) }
        binding.rvSearchReportHorizontalContent.adapter = rvAdapter
        binding.rvSearchReportHorizontalContent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvSearchReportHorizontalContent.addOnScrollListener(object :
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
                    viewModel.getSearchReportData(
                        lastReportId
                    )
                }
            }
        })
    }


    private fun initToggleButton() {
        binding.ibSearchReportHorizontalSort.setOnClickListener {
            toggleLayoutMode()
        }
    }

    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            while (binding.rvSearchReportHorizontalContent.itemDecorationCount > 0) {
                binding.rvSearchReportHorizontalContent.removeItemDecorationAt(0)
            }

            binding.rvSearchReportHorizontalContent.addItemDecoration(SearchSpacingItemDecoration(10))
            binding.rvSearchReportHorizontalContent.layoutManager = GridLayoutManager(requireContext(), 2)
            rvAdapter.setGridMode(true)
            binding.ibSearchReportHorizontalSort.setImageResource(R.drawable.ic_search_grid_sort)

        } else {
            binding.rvSearchReportHorizontalContent.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvAdapter.setGridMode(false)
            binding.ibSearchReportHorizontalSort.setImageResource(R.drawable.ic_search_horizontal_sort)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.cgSearchReportGroupFilters.removeAllViews()
        _binding = null
    }

}
