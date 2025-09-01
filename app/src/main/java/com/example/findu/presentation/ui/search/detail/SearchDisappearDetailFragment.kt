package com.example.findu.presentation.ui.search.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toDetailSearchRvTag
import com.example.findu.databinding.FragmentSearchDetailDisappearBinding
import com.example.findu.domain.model.search.DetailReportData
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.viewmodel.DetailReportViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchDisappearDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchDetailDisappearBinding
    private val viewModel by viewModels<DetailReportViewModel>()
    private var cardId: Long = -1
    private var tag: String? = null
    private var name: String? = null

    private val args: SearchDisappearDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchDetailDisappearBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardId = args.id.ifBlank { cardId.toString() }.toLong()
        tag = args.tag.ifBlank { tag }
        name = args.name.ifBlank { name }

        if (cardId == -1L || tag == null) {
            Toast.makeText(requireContext(), "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()

            requireActivity().supportFragmentManager.popBackStack()
            return
        }

        initDummyImages()
//        observeViewModel()
//        fetchDetailData()
        initListener()
    }

    private fun initDummyImages() {
        val dummyImages = listOf(
            R.drawable.img_search_detail_content,
            R.drawable.img_search_detail_content,
            R.drawable.img_search_detail_content
        )
        initViewPager(dummyImages)
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
//            tvDetailTitleField.text = name
//            tvDetailTagField.text = convertTagToKorean(data.tag.text)
            tvValueAge.text = data.age
            tvValueGender.text = data.sex
            tvValueLostDate.text = data.eventDate
            tvValueRfid.text = data.rfid
            tvSpecialNote.text = data.specialNote
            tvValueLostLocationAround.text = data.surroundPlace
            tvValueLostLocation.text = data.eventLocation
            tvValueReporterName.text = data.userName
            tvValuePhoneNumber.text = data.userPhone

//            initViewPager(data.imageUrls)
            initTagView(data)
            initBookmarkUI(data)
//            initMapButtons(data)
        }
    }


//    private fun initMapButtons(data: DetailReportData) {
//        binding.btnViewLocation.setOnClickListener {
//            openNaverMap(data.eventLocation)
//        }
//        binding.btnShowFoundPlace.setOnClickListener {
//            openNaverMap(data.eventLocation)
//        }
//    }

    private fun initViewPager(imageList: List<Int>) {
        val adapter = SearchDetailVPAdapter(imageList)
        binding.vpSearchDetailImg.adapter = adapter
        binding.vpSearchDetailImg.setCurrentItem(0, false)

        binding.vpSearchDetailImg.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2

            setPageTransformer(
                MarginPageTransformer(
                    resources.getDimensionPixelOffset(R.dimen.SEARCH_IMAGE_MARGIN)
                )
            )
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