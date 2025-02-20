package com.example.findu.presentation.ui.search.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toDetailSearchRvTag
import com.example.findu.databinding.FragmentSearchDetailProtectingBinding
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.presentation.ui.search.viewmodel.DetailSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailProtectingBinding
    private var isDetailVisible = false
    private val viewModel by viewModels<DetailSearchViewModel>()
    private var cardId: Long = -1
    private var tag: String? = null
    private var name: String? = null

    private val args: SearchProtectingDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDetailProtectingBinding.inflate(layoutInflater)
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
        observeViewModel()
        fetchDetailData()

        setContentVisibility()
        initBackButton()

    }

    private fun fetchDetailData() {
        when (tag) {
            "보호중" -> viewModel.getDetailSearchProtect(cardId)
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
                    Log.e("DetailSearchViewModel", it)
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUI(data: DetailProtectData) {
        binding.apply {
            Glide.with(requireContext()).load(data.imageUrl).into(ivSearchDetailImg)
            tvSearchContentDetailTitle.text = name
            tvDetailTagField.text = convertTagToKorean(data.tag.text)
            tvDetailBreedField.text = data.breed
            tvDetailAgeField.text = data.age
            tvDetailWeightField.text = data.weight
            tvDetailSexField.text = data.sex
            tvDetailHappenDateField.text = data.happenDate
            tvDetailFurColorField.text = data.furColor
            tvDetailNeuteringField.text = data.neutering
            tvDetailSignificantField.text = data.significant
            tvDetailNoticeNumberField.text = data.noticeNumber
            tvDetailNoticeDurationField.text = data.noticeDuration
            tvDetailFoundLocationField.text = data.foundLocation
            tvDetailCareNameField.text = data.careName
            tvDetailCareTelField.text = data.careTel
            tvDetailAuthorityField.text = data.authority
            tvDetailAuthorityPhoneNumberField.text = data.authorityPhoneNumber

            initTagView(data)
            initBookmarkUI(data)
            initCallButtons(data)
            initMapButtons(data)
        }

    }


    private fun initBackButton() {
        binding.ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initCallButtons(data: DetailProtectData) {
        binding.tvDetailCareTelField.setOnClickListener {
            call(data.careTel)
        }

        binding.tvDetailAuthorityPhoneNumberField.setOnClickListener {
            call(data.authorityPhoneNumber)
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

    private fun initMapButtons(data: DetailProtectData) {
        binding.btnViewLocation.setOnClickListener {
            openNaverMap(data.careAddr)
        }
        binding.btnShowFoundPlace.setOnClickListener {
            openNaverMap(data.foundLocation)
        }
    }

    private fun initBookmarkUI(data: DetailProtectData) {
        updateBookmarkUI(data.interest)
        binding.ivSearchDetailBookmark.setOnClickListener {
            data.interest = !data.interest
            viewModel.setInterestProtectingAnimal(cardId)
            updateBookmarkUI(data.interest)
        }
    }

    private fun initTagView(data: DetailProtectData) {
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

    private fun setContentVisibility() {
        binding.clSearchShowMore.setOnClickListener {
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