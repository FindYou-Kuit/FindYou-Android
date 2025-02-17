package com.example.findu.presentation.ui.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.findu.R
import com.example.findu.data.mapper.todomain.toDetailSearchRvTag
import com.example.findu.databinding.FragmentSearchDetailProtectingBinding
import com.example.findu.domain.model.search.DetailSearchData
import com.example.findu.presentation.ui.search.model.DetailSearchRv
import com.example.findu.presentation.ui.search.viewmodel.DetailSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailProtectingBinding
    private var isDetailVisible = false
    private val viewModel by viewModels<DetailSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDetailProtectingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable("selectedItem") as? DetailSearchRv
        if (item == null) {
            requireActivity().supportFragmentManager.popBackStack()
            return
        }
//        observeViewModel()
//        viewModel.getDetailSearchProtect()

        initTagView(item)
        initBookmarkUI(item)
        setContentVisibility()
        initBackButton()
        initMapButtons(item)
        initCallButtons()

    }


    private fun initBackButton() {
        binding.ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun initCallButtons() {
        binding.tvDetailCareTelField.setOnClickListener {
            call(binding.tvDetailCareTelField.text.toString())
        }

        binding.tvDetailAuthorityPhoneNumberField.setOnClickListener {
            call(binding.tvDetailAuthorityPhoneNumberField.text.toString())
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

    private fun initMapButtons(item: DetailSearchRv) {
        binding.btnViewLocation.setOnClickListener {
            openNaverMap(item.foundLocation)
        }
        binding.btnShowFoundPlace.setOnClickListener {
            openNaverMap(item.foundLocation)
        }
    }

    private fun initBookmarkUI(item: DetailSearchRv) {
        updateBookmarkUI(item.interest)
        binding.ivSearchDetailBookmark.setOnClickListener {
            item.interest = !item.interest
            updateBookmarkUI(item.interest)
        }
    }

    private fun initTagView(item: DetailSearchRv) {
        item.let {
            binding.tvDetailTagField.text = item.tag.text
            binding.tvDetailTagField.setTextColor(requireContext().getColor(item.tag.textColor))
            binding.tvDetailTagField.setBackgroundResource(item.tag.backgroundRes)
            binding.tvDetailBreedField.text = it.breed
            binding.tvDetailHappenDateField.text = it.happenDate
            binding.tvDetailFoundLocationField.text = it.foundLocation
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