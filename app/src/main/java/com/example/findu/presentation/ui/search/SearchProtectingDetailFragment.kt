package com.example.findu.presentation.ui.search

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchDetailProtectingBinding
import com.example.findu.presentation.ui.search.model.SearchDetailData
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.example.findu.presentation.ui.search.model.SearchData

class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailProtectingBinding
    private var isDetailVisible = false
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
        item.let {
            binding.tvSearchDetailTag.text = item.status.text
            binding.tvSearchDetailTag.setTextColor(requireContext().getColor(item.status.textColor))
            binding.tvSearchDetailTag.setBackgroundResource(item.status.backgroundRes)

            binding.tvSearchDetailName.text = it.name
            binding.tvSearchContentPostDate.text = it.date
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

        binding.tvProtectCenterPhoneNumber.setOnClickListener {
            call(binding.tvProtectCenterPhoneNumber.text.toString())
        }

        binding.tvJurisdictionPhoneNumber.setOnClickListener {
            call(binding.tvJurisdictionPhoneNumber.text.toString())
        }

        binding.btnViewLocation.setOnClickListener {
            openNaverMap(item.address)
        }
        binding.btnShowFoundPlace.setOnClickListener{
            openNaverMap(item.address)
        }

    }
    @SuppressLint("QueryPermissionsNeeded")
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


    private fun call(phoneNumber: String) {
        if (phoneNumber.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
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