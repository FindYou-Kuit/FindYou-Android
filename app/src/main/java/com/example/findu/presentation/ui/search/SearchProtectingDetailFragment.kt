package com.example.findu.presentation.ui.search

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
import com.example.findu.presentation.ui.search.model.DetailSearchRv
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.model.SearchRv

class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailProtectingBinding
    private var isDetailVisible = false
    private val imageList = listOf(
        DetailSearchRv(R.drawable.img_search_detail_witness_content),
        DetailSearchRv(R.drawable.img_search_detail),
        DetailSearchRv(R.drawable.img_search_detail),
        DetailSearchRv(R.drawable.img_search_detail_witness_content)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDetailProtectingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable("selectedItem") as? SearchRv
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

    private fun initMapButtons(item: SearchRv) {
        binding.btnViewLocation.setOnClickListener {
            openNaverMap(item.address)
        }
        binding.btnShowFoundPlace.setOnClickListener {
            openNaverMap(item.address)
        }
    }

    private fun initBookmarkUI(item: SearchRv) {
        updateBookmarkUI(item.isBookmark)
        binding.ivSearchDetailBookmark.setOnClickListener {
            item.isBookmark = !item.isBookmark
            updateBookmarkUI(item.isBookmark)
        }
    }

    private fun initTagView(item: SearchRv) {
        item.let {
            binding.tvDetailTagField.text = item.status.text
            binding.tvDetailTagField.setTextColor(requireContext().getColor(item.status.textColor))
            binding.tvDetailTagField.setBackgroundResource(item.status.backgroundRes)
            binding.tvDetailBreedField.text = it.name
            binding.tvDetailHappenDateField.text = it.date
            binding.tvDetailFoundLocationField.text = it.address
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