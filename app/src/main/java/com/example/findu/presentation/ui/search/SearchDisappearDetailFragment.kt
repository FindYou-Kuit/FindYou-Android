package com.example.findu.presentation.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchDetailDisappearBinding
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.model.SearchData
import com.example.findu.presentation.ui.search.model.SearchDetailData

class SearchDisappearDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchDetailDisappearBinding
    private var isBookmark = false
    private val imageList = listOf(
        SearchDetailData(R.drawable.img_search_detail),
        SearchDetailData(R.drawable.img_search_detail),
        SearchDetailData(R.drawable.img_search_detail),
        SearchDetailData(R.drawable.img_search_detail)
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchDetailDisappearBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getSerializable("selectedItem") as? SearchData
        if (item == null) {
            requireActivity().supportFragmentManager.popBackStack() // 데이터 없으면 뒤로가기
            return
        }
        item.let {
            binding.tvSearchDetailName.text = it.name
            binding.tvSearchContentDetailReportDate.text = it.date
            binding.tvSearchContentDetailRescueLocation.text = it.address
        }

        updateBookmarkUI(item.isBookmark)
        binding.ivSearchDetailBookmark.setOnClickListener {
            item.isBookmark = !item.isBookmark
            updateBookmarkUI(item.isBookmark)
        }

        setContentVisibility()
        binding.ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val adapter = SearchDetailVPAdapter(imageList)
        binding.vpSearchDetailImg.adapter = adapter
        binding.vpSearchDetailDots.attachTo(binding.vpSearchDetailImg)

    }

    private fun updateBookmarkUI(bookmark: Boolean) {
        binding.ivSearchDetailBookmark.setImageResource(
            if (bookmark) R.drawable.ic_search_content_fill_bookmark
            else R.drawable.ic_search_content_blank_bookmark
        )
    }

    private fun setContentVisibility() {
        binding.clSearchShowMore.setOnClickListener(){
            binding.clSearchContentDetail.visibility = View.VISIBLE
            binding.clSearchShowMore.visibility = View.INVISIBLE
        }

    }

    private fun initBookmark() {
        isBookmark = requireContext()
            .getSharedPreferences("Bookmarks", Context.MODE_PRIVATE)
            .getBoolean("search_detail_bookmark", false)
        binding.ivSearchDetailBookmark.setImageResource(
            if (isBookmark) R.drawable.ic_search_content_fill_bookmark else R.drawable.ic_search_content_blank_bookmark
        )

        binding.ivSearchDetailBookmark.setOnClickListener {
            isBookmark = !isBookmark
            binding.ivSearchDetailBookmark.setImageResource(
                if (isBookmark) R.drawable.ic_search_content_fill_bookmark else R.drawable.ic_search_content_blank_bookmark
            )
            requireContext()
                .getSharedPreferences("Bookmarks", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("search_detail_bookmark", isBookmark)
                .apply()
        }
    }


}