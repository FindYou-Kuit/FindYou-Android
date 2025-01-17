package com.example.findu.presentation.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchProtectingDetailBinding
import com.example.findu.presentation.ui.search.model.SearchDetailData
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter

class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchProtectingDetailBinding
    private var isBookmark = false
    private var isDetailVisible = false
    private val imageList = listOf(
        SearchDetailData(R.drawable.img_search_detail),
        SearchDetailData(R.drawable.img_search_detail),
        SearchDetailData(R.drawable.img_search_detail)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchProtectingDetailBinding.inflate(layoutInflater)
        return binding.root
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
            binding.clSearchShowMore.visibility = View.GONE
        }

        binding.clSearchDetailSpecialNoteBtn.setOnClickListener {
            isDetailVisible = !isDetailVisible
            binding.clSearchDetailSpecialNoteDescription.visibility = if (isDetailVisible) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.tvSearchDetailSpecialNote.text = if (isDetailVisible) {
                "접기"
            } else {
                "보기"
            }

            binding.ivSearchDetailSpecialNoteIcon.rotation = if (isDetailVisible) {
                180f
            } else {
                0f
            }
        }

    }
}