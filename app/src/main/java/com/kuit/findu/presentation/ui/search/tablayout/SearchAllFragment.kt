package com.kuit.findu.presentation.ui.search.tablayout

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
import com.kuit.findu.R
import com.kuit.findu.data.mapper.todomain.toSearchRvTag
import com.kuit.findu.databinding.FragmentSearchAllBinding
import com.kuit.findu.domain.model.search.SearchAnimal
import com.kuit.findu.presentation.ui.search.BundleTag.SELECTED_FILTER_DATA
import com.kuit.findu.presentation.ui.search.SearchFragmentDirections
import com.kuit.findu.presentation.ui.search.SearchSpacingItemDecoration
import com.kuit.findu.presentation.ui.search.adapter.SearchListAdapter
import com.kuit.findu.presentation.ui.search.adapter.SearchListListener
import com.kuit.findu.presentation.ui.search.model.SearchFilterUiModel
import com.kuit.findu.presentation.ui.search.model.SearchRv
import com.kuit.findu.presentation.ui.search.model.SearchType
import com.kuit.findu.presentation.ui.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchAllFragment : Fragment(), SearchListListener {

    private var _binding: FragmentSearchAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var listAdapter: SearchListAdapter
    private var isGridMode = false
    private val viewModel by viewModels<SearchViewModel>()

    private var lastId = Long.MAX_VALUE

    private var isNewList = false
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchAllBinding.inflate(inflater, container, false)
        initRVAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
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
                isLoading = false
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
                isLoading = false
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
                reportId = item.reportId,
                createdAt = item.createdAt,
            )
        }

        if (isNewList) {
            listAdapter.submitContent(searchList)
            isNewList = false
            binding.rvSearchAll.post {
                binding.rvSearchAll.scrollToPosition(0)
            }
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
        val action =
            SearchFragmentDirections.actionFragmentSearchToFragmentSearchFilter(SearchType.ALL)
        findNavController().navigate(action)
    }

    private fun initRVAdapter() {
        listAdapter = SearchListAdapter(this)
        binding.rvSearchAll.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
            setHasFixedSize(true)
            itemAnimator = null
        }

        binding.rvSearchAll.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPos = layoutManager.findLastVisibleItemPosition()
                val total = (recyclerView.adapter?.itemCount ?: 1) - 1

                if (lastPos >= total - 2 && !isLoading) {
                    isLoading = true
                    Log.d("SearchFragment", "페이징 요청 발생 (lastId=$lastId)")
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

    // 필터 버튼 클릭 시 검색 필터 화면으로 이동
    override fun onFilterClick() = navigateToFilter()

    // 정렬 버튼 클릭 시 리스트 모드 전환
    override fun onToggleClick() = toggleLayoutMode()

    // 아이템 항목 클릭 시 상세 화면으로 이동
    override fun onItemClick(item: SearchRv) =
        navigateToDetail(item.reportId, item.tag.text, item.name)

    // 관심 등록/해제 버튼 클릭 시 상태 반영
    override fun onBookmarkClick(id: Long, isBookmark: Boolean, tag: String) =
        viewModel.setInterest(id, isBookmark, tag)

    // 상단 배너 클릭 시 정보 화면으로 이동
    override fun onBannerClick() =
        findNavController().navigate(R.id.action_fragment_search_to_adoptInfoFragment)

    // 배너에 사용할 이미지 리소스 반환
    override fun getBannerRes(): Int = R.drawable.img_search_banner_adopt
}
