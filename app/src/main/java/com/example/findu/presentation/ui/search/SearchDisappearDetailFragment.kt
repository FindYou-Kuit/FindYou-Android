package com.example.findu.presentation.ui.search

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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

        binding.btnViewLocation.setOnClickListener(){
            openNaverMap(item.address)
        }
        binding.btnShowFoundPlace.setOnClickListener(){
            openNaverMap(item.address)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openNaverMap(address: String) {
        if (address.isNotEmpty()) {
            val encodedAddress = Uri.encode(address)
            val uri = Uri.parse("nmap://search?query=$encodedAddress&appname=${requireContext().packageName}")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            // 네이버 지도 앱이 설치되어 있는지
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                // 플레이스토어 앱이 없을 경우
                try {
                    val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap"))
                    startActivity(playStoreIntent)
                } catch (e: ActivityNotFoundException) {
                    // 웹 브라우저에서 네이버 지도 다운로드 페이지로
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nhn.android.nmap"))
                    startActivity(webIntent)
                }
            }
        }
    }


    private fun updateBookmarkUI(bookmark: Boolean) {
        binding.ivSearchDetailBookmark.setImageResource(
            if (bookmark) R.drawable.ic_search_content_fill_bookmark
            else R.drawable.ic_search_content_blank_bookmark
        )
    }

    private fun setContentVisibility() {
        binding.clSearchShowMore.setOnClickListener() {
            binding.clSearchContentDetail.visibility = View.VISIBLE
            binding.clSearchShowMore.visibility = View.INVISIBLE
        }

    }


}