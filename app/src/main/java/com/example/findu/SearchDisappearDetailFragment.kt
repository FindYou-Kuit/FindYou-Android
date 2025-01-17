package com.example.findu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findu.databinding.FragmentSearchDisappearDetailBinding
import com.example.findu.databinding.FragmentSearchProtectingDetailBinding
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.model.SearchDetailData

class SearchDisappearDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchDisappearDetailBinding
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
        binding = FragmentSearchDisappearDetailBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBookmark()
        setContentVisibility()
        binding.ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val adapter = SearchDetailVPAdapter(imageList)
        binding.vpSearchDetailImg.adapter = adapter
        binding.vpSearchDetailDots.attachTo(binding.vpSearchDetailImg)

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