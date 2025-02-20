package com.example.findu.presentation.ui.search.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toDetailSearchRvTag
import com.example.findu.databinding.FragmentSearchDetailWitnessBinding
import com.example.findu.domain.model.search.DetailReportData
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.viewmodel.DetailReportViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchWitnessDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailWitnessBinding
    private val viewModel by viewModels<DetailReportViewModel>()
    private var cardId: Long = -1
    private var tag: String? = null
    private var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDetailWitnessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cardId = it.getLong("cardId", -1)
            tag = it.getString("tag")
            name = it.getString("name")
        }

        if (cardId == -1L || tag == null) {
            Toast.makeText(requireContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
            return
        }

        observeViewModel()
        fetchDetailData()
        initListener()

    }

    private fun fetchDetailData() {
        when (tag) {
            "목격신고", "실종신고" -> viewModel.getDetailSearchReport(cardId)
            else -> {
                Toast.makeText(requireContext(), "잘못된 태그 값입니다.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.detailSearchData.collectLatest { data ->
                data?.let { updateUI(it) }
            }
        }

        lifecycleScope.launch {
            viewModel.errorMessage.collectLatest { message ->
                message?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUI(data: DetailReportData) {
        binding.apply {
            tvDetailTitleField.text = name
            tvDetailTagField.text = convertTagToKorean(data.tag.text)
            tvDetailBreedField.text = data.breed
            tvDetailFurColorField.text = data.furColor
            tvDetailUserNameField.text = data.userName
            tvDetailWriteDateField.text = data.writeDate
            tvDetailWitnessDateField.text = data.eventDate
            tvDetailEventDateField.text = data.eventDate
            tvDetailEventLocationField.text = data.eventLocation
            tvDetailAdditionalDescriptionField.text = data.additionalDescription

            initViewPager(data.imageUrls)
            initTagView(data)
            initBookmarkUI(data)
            initMapButtons(data)
            initFeatureChips(data.features)
        }
    }

    private fun initFeatureChips(features: List<String>) {
        val chipGroup = binding.cgSearchGroupFeature
        chipGroup.removeAllViews()

        features.forEach { feature ->
            val chip = layoutInflater.inflate(R.layout.item_search_features_chip, chipGroup, false) as Chip
            chip.text = feature
            chipGroup.addView(chip)
        }
    }


    private fun initViewPager(imageList: List<String>) {
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

    private fun initMapButtons(data: DetailReportData) {
        binding.btnViewLocation.setOnClickListener {
            openNaverMap(data.eventLocation)
        }
        binding.btnShowFoundPlace.setOnClickListener {
            openNaverMap(data.eventLocation)
        }
    }

    private fun initListener() {
        binding.ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initBookmarkUI(data: DetailReportData) {
        updateBookmarkUI(data.interest)
        binding.ivSearchDetailBookmark.setOnClickListener {
            data.interest = !data.interest
            viewModel.setInterestReportAnimal(cardId)
            updateBookmarkUI(data.interest)
        }
    }

    private fun initTagView(data: DetailReportData) {
        val koreanTag = convertTagToKorean(data.tag.toString())
        binding.tvDetailTagField.text = koreanTag

        val tagInfo = data.tag.toDetailSearchRvTag()
        binding.tvDetailTagField.setTextColor(requireContext().getColor(tagInfo.textColor))
        binding.tvDetailTagField.setBackgroundResource(tagInfo.backgroundRes)
    }

    private fun convertTagToKorean(tag: String?): String {
        return when (tag) {
            "WITNESS" -> "목격신고"
            "MISSING" -> "실종신고"
            "PROTECTING" -> "보호중"
            else -> tag ?: "알 수 없음"
        }
    }

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

}