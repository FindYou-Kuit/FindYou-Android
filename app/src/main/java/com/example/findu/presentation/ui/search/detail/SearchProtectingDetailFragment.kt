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
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
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

    private var isBookmarked = false

    private lateinit var mapView: MapView
    private var naverMap: NaverMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchDetailProtectingBinding.inflate(layoutInflater)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync { nMap ->
            naverMap = nMap
            setupMap()
        }
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
        initBookmarkUI()
//        setContentVisibility()
        initListener()

    }

    private fun setupMap() {
        val address = binding.tvValueProtectLocation.text.toString()
        if (address.isNotEmpty()) {
            try {
                val geocoder = android.location.Geocoder(requireContext())
                val results = geocoder.getFromLocationName(address, 1)
                if (!results.isNullOrEmpty()) {
                    val location = LatLng(results[0].latitude, results[0].longitude)
                    val cameraUpdate = CameraUpdate.scrollTo(location)
                    naverMap?.moveCamera(cameraUpdate)

                    val marker = Marker().apply {
                        position = location
                        map = naverMap
                        icon = OverlayImage.fromResource(R.drawable.ic_search_map_marker)
                        height = 23
                    }
                    marker.position = location
                    marker.map = naverMap
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "주소 찾을 수 업음.", Toast.LENGTH_SHORT).show()
            }
        }
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
            tvDetailTagField.text = convertTagToKorean(data.tag.text)
            tvValueName.text = data.breed
            tvValueAge.text = data.age
            tvValueWeight.text = data.weight
            tvValueGender.text = data.sex
            tvValueNeuter.text = data.happenDate
            tvValueHairColor.text = data.furColor
            tvSpecialNote.text = data.specialNote
            tvShelterLocation.text = data.careAddr
            tvValueShelterName.text = data.careName
            tvValueNotiDate.text = data.noticeDuration
            tvValueNotiNum.text = data.noticeNumber
            tvValueShelterPhoneNumber.text = data.careTel
            tvValueJurisdiction.text = data.authority

            initTagView(data)
//            initBookmarkUI(data)
//            initCallButtons(data)
//            initMapButtons(data)
        }

    }


    private fun initListener() = with(binding) {
        ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        initBookmarkUI()

        llCallPhone.setOnClickListener {
            val phoneNumber = binding.tvValueShelterPhoneNumber.text.toString()
            call(phoneNumber)
        }

        llViewMap.setOnClickListener {
            val address = binding.tvValueProtectLocation.text.toString()
            openNaverMap(address)
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

//    private fun initMapButtons(data: DetailProtectData) {
//        binding.btnViewLocation.setOnClickListener {
//            openNaverMap(data.careAddr)
//        }
//        binding.btnShowFoundPlace.setOnClickListener {
//            openNaverMap(data.foundLocation)
//        }
//    }

    private fun initBookmarkUI() {
        binding.ivSearchDetailBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            viewModel.setInterestProtectingAnimal(cardId)
            updateBookmarkUI(isBookmarked)
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
            else R.drawable.ic_search_detail_blank_bookmark
        )
    }

}