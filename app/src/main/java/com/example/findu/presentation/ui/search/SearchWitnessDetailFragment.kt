package com.example.findu.presentation.ui.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchDetailWitnessBinding
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.model.SearchData
import com.example.findu.presentation.ui.search.model.SearchDetailData


class SearchWitnessDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailWitnessBinding
    private val imageList = listOf(
        SearchDetailData(R.drawable.img_search_detail_witness_content),
        SearchDetailData(R.drawable.img_search_detail_witness_content),
        SearchDetailData(R.drawable.img_search_detail_witness_content),
        SearchDetailData(R.drawable.img_search_detail_witness_content)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDetailWitnessBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable("selectedItem") as? SearchData
        if (item == null) {
            requireActivity().supportFragmentManager.popBackStack()
            return
        }
        initTagView(item)
        initBookmarkUI(item)
        initListener()
        initMapButtons(item)
        initViewPager()

    }

    private fun initViewPager(){
        val adapter = SearchDetailVPAdapter(imageList)
        binding.vpSearchDetailImg.adapter = adapter
        binding.vpSearchDetailImg.setCurrentItem(1, false)
        val indicatorCount = imageList.size
        val pageIndicators = Array(indicatorCount) { View(requireContext()) }
        val indicatorContainer = binding.llDotsContainer

        indicatorContainer.removeAllViews()
        for (i in pageIndicators.indices) {
            val indicator = View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(6, 6).apply {
                    marginStart = 3
                    marginEnd = 3
                }
                setBackgroundResource(R.drawable.ic_search_indicator_inactive)
            }
            indicatorContainer.addView(indicator)
            pageIndicators[i] = indicator
        }
        pageIndicators[0].setBackgroundResource(R.drawable.ic_search_indicator_active)

        binding.vpSearchDetailImg.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val realPosition = when (position) {
                    0 -> imageList.size - 1
                    imageList.size + 1 -> 0
                    else -> position - 1
                }

                pageIndicators.forEach { it.setBackgroundResource(R.drawable.ic_search_indicator_inactive) }
                pageIndicators[realPosition].setBackgroundResource(R.drawable.ic_search_indicator_active)

                binding.vpSearchDetailImg.postDelayed({
                    when (position) {
                        0 -> binding.vpSearchDetailImg.setCurrentItem(imageList.size, false)
                        imageList.size + 1 -> binding.vpSearchDetailImg.setCurrentItem(1, false)
                    }
                }, 200)
            }
        })
    }

    private fun initMapButtons(item: SearchData) {
        binding.btnViewLocation.setOnClickListener{
            openNaverMap(item.address)
        }
        binding.btnShowFoundPlace.setOnClickListener{
            openNaverMap(item.address)
        }
    }

    private fun initListener() {
        binding.clSearchShowMore.setOnClickListener{
            binding.clSearchContentDetail.visibility = View.VISIBLE
            binding.clSearchShowMore.visibility = View.INVISIBLE
        }
        binding.ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initBookmarkUI(item: SearchData) {
        updateBookmarkUI(item.isBookmark)
        binding.ivSearchDetailBookmark.setOnClickListener {
            item.isBookmark = !item.isBookmark
            updateBookmarkUI(item.isBookmark)
        }
    }

    private fun initTagView(item: SearchData) {
        item.let {
            binding.tvSearchDetailTag.text = item.status.text
            binding.tvSearchDetailTag.setTextColor(requireContext().getColor(item.status.textColor))
            binding.tvSearchDetailTag.setBackgroundResource(item.status.backgroundRes)

            binding.tvSearchDetailName.text = it.name
            binding.tvSearchDetailFoundDate.text = it.date
            binding.tvSearchContentDetailRescueLocationContent.text = it.address
        }
    }

    private fun openNaverMap(address: String) {
        if (address.isNotEmpty()) {
            val encodedAddress = Uri.encode(address)
            val uri = Uri.parse("nmap://search?query=$encodedAddress&appname=${requireContext().packageName}")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                try {
                    val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap"))
                    startActivity(playStoreIntent)
                } catch (e: ActivityNotFoundException) {
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nhn.android.nmap"))
                    startActivity(webIntent)
                }
            }
        }
    }

    private fun updateBookmarkUI(bookmark: Boolean) {
        binding.ivSearchDetailBookmark.setImageResource(
            if (bookmark) R.drawable.ic_search_fill_bookmark
            else R.drawable.ic_search_blank_bookmark
        )
    }

}