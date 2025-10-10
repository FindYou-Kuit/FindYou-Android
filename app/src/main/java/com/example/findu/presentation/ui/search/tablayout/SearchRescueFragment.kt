package com.example.findu.presentation.ui.search.tablayout

import android.os.Bundle
import android.util.Log
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
import com.example.findu.databinding.FragmentSearchRescueBinding
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.example.findu.presentation.ui.search.SearchFragmentDirections
import com.example.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.example.findu.presentation.ui.search.adapter.SearchListAdapter
import com.example.findu.presentation.ui.search.adapter.SearchListListener
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.SearchRv
import com.example.findu.presentation.ui.search.model.SearchType
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchRescueFragment : Fragment(), SearchListListener {

    private var _binding: FragmentSearchRescueBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: SearchListAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()

    private var lastProtectId = Long.MAX_VALUE
    private var isNewList = false

    private var items = ArrayList<SearchAnimal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchRescueBinding.inflate(inflater, container, false)
        initRVAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        observeFilterResult()

        viewModel.getSearchData(SearchType.PROTECTING, lastProtectId)
    }

    private fun observeFilterResult() {
        findNavController().currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<SearchFilterUiModel>(SELECTED_FILTER_DATA)
            ?.observe(viewLifecycleOwner) { selected ->
                Log.d("SearchFilter", "필터 수신됨: $selected")

                viewModel.updateProtectFilterState(selected)
                isNewList = true
                lastProtectId = Long.MAX_VALUE

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(50)
                    viewModel.getSearchData(SearchType.PROTECTING, lastProtectId)
                }

                findNavController().currentBackStackEntry
                    ?.savedStateHandle
                    ?.remove<SearchFilterUiModel>(SELECTED_FILTER_DATA)
            }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.protectSearchData.collectLatest { searchResults ->
                if (!searchResults.isNullOrEmpty()) {
                    val animals = searchResults.flatMap { it.cards }
                    setupRV(animals)
                    lastProtectId = searchResults.last().lastId
                } else {
                    setupRV(emptyList())
                    lastProtectId = Long.MAX_VALUE
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
                location = item.location,
                isBookmark = item.interest,
                tag = item.tag.toSearchRvTag(),
                reportId = item.reportId
            )
        }

        if (isNewList) {
            listAdapter.submitContent(searchList)
            isNewList = false
            binding.rvSearchRescue.scrollToPosition(0)
            binding.rvSearchRescue.smoothScrollToPosition(0)
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
        val action = SearchFragmentDirections
            .actionFragmentSearchToFragmentSearchFilter(SearchType.PROTECTING)
        findNavController().navigate(action)
    }

    private fun initRVAdapter() {
        listAdapter = SearchListAdapter(this)


        binding.rvSearchRescue.apply {
            adapter = listAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            itemAnimator = null
        }

        binding.rvSearchRescue.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastPos = when (val manager = recyclerView.layoutManager) {
                    is LinearLayoutManager -> manager.findLastVisibleItemPosition()
                    else -> return
                }
                val total = (recyclerView.adapter?.itemCount ?: 1) - 1
                //페이징 처리
                if (lastPos == total) {
                    viewModel.getSearchData(SearchType.PROTECTING, lastProtectId)
                }
            }
        })
    }


    private fun toggleLayoutMode() {
        isGridMode = !isGridMode

        if (isGridMode) {
            while (binding.rvSearchRescue.itemDecorationCount > 0) {
                binding.rvSearchRescue.removeItemDecorationAt(0)
            }
            binding.rvSearchRescue.addItemDecoration(SearchSpacingItemDecoration(10))

            val grid = GridLayoutManager(requireContext(), 2)

            grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (position == 0) 2 else 1
            }

            binding.rvSearchRescue.layoutManager = grid
            listAdapter.setGridMode(true)
        } else {
            binding.rvSearchRescue.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listAdapter.setGridMode(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvSearchRescue.adapter = null
        _binding = null
    }

    override fun onFilterClick() = navigateToFilter()
    override fun onToggleClick() = toggleLayoutMode()
    override fun onItemClick(item: SearchRv) =
        navigateToDetail(item.reportId, item.tag.text, item.name)

    override fun onBookmarkClick(id: Long, isBookmark: Boolean, tag: String) =
        viewModel.setInterest(id, isBookmark, tag)

    override fun onBannerClick() =
        findNavController().navigate(R.id.action_fragment_search_to_adoptInfoFragment)

    override fun getBannerRes(): Int = R.drawable.img_search_banner_adopt

}
