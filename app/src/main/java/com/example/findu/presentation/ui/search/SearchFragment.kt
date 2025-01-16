package com.example.findu.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchBinding
import com.example.findu.presentation.ui.search.model.SearchData
import com.example.findu.presentation.ui.search.adapter.SearchVPAdapter
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {
    private val tabTextList = listOf("전체 보기", "구조 동물 조회", "신고 동물 조회")

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initViewPager()
        return binding.root
    }

    private fun initViewPager() {
        binding.vpSearchContent.adapter = SearchVPAdapter(requireActivity())
        TabLayoutMediator(binding.tlSearchTab, binding.vpSearchContent) { tab, position ->
            tab.text = tabTextList[position]
            tab.contentDescription = tabTextList[position]
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}