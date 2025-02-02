package com.example.findu.presentation.ui.search

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchDetailProtectingBinding
import com.example.findu.presentation.ui.search.model.SearchDetailData
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.model.SearchData

class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailProtectingBinding
    private var isDetailVisible = false
    private val imageList = listOf(
        SearchDetailData(R.drawable.img_search_detail_witness_content),
        SearchDetailData(R.drawable.img_search_detail),
        SearchDetailData(R.drawable.img_search_detail),
        SearchDetailData(R.drawable.img_search_detail_witness_content)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchDetailProtectingBinding.inflate(layoutInflater)
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
        setContentVisibility()
        initBackButton()
        initMapButtons(item)
        initCallButtons()
        initViewPager()

    }

    private fun initViewPager() {
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

    private fun initBackButton() {
        binding.ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initCallButtons() {
        binding.tvProtectCenterPhoneNumber.setOnClickListener {
            call(binding.tvProtectCenterPhoneNumber.text.toString())
        }

        binding.tvJurisdictionPhoneNumber.setOnClickListener {
            call(binding.tvJurisdictionPhoneNumber.text.toString())
        }
    }

    private fun call(phoneNumber: String) {
        if (phoneNumber.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        }
    }

    private fun initMapButtons(item: SearchData) {
        binding.btnViewLocation.setOnClickListener {
            openNaverMap(item.address)
        }
        binding.btnShowFoundPlace.setOnClickListener {
            openNaverMap(item.address)
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
            binding.tvSearchContentPostDate.text = it.date
            binding.tvSearchContentDetailRescueLocation.text = it.address
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openNaverMap(address: String) {
        if (address.isNotEmpty()) {
            val encodedAddress = Uri.encode(address)
            val uri =
                Uri.parse("nmap://search?query=$encodedAddress&appname=${requireContext().packageName}")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                try {
                    val playStoreIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.nhn.android.nmap")
                    )
                    startActivity(playStoreIntent)
                } catch (e: ActivityNotFoundException) {
                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.nhn.android.nmap")
                    )
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

    private fun setContentVisibility() {
        binding.clSearchShowMore.setOnClickListener() {
            binding.clSearchContentDetail.visibility = View.VISIBLE
            binding.clSearchShowMore.visibility = View.INVISIBLE
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