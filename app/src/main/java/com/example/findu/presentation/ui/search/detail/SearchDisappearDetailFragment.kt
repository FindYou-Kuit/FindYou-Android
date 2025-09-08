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
class SearchDisappearDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchDetailDisappearBinding
    private val viewModel by viewModels<DetailReportViewModel>()
    private var cardId: Long = -1
    private var tag: String? = null
    private var name: String? = null

    private val args: SearchDisappearDetailFragmentArgs by navArgs()
    private var isBookmarked = false

    private lateinit var mapView: MapView
    private var naverMap: NaverMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchDetailDisappearBinding.inflate(layoutInflater)

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
//        observeViewModel()
//        fetchDetailData()
        initListener()
    }

    private fun setupMap() {
        val address = binding.tvValueLostLocation.text.toString()
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

            initTagView(data)
        }
    }

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


    private fun initListener() = with(binding) {
        initDummyImages()

        ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        initBookmarkUI()

        llSendMessage.setOnClickListener {
            Toast.makeText(requireContext(), "준비 중이에요!", Toast.LENGTH_SHORT).show()
        }

        llViewMap.setOnClickListener {
            val address = binding.tvValueLostLocation.text.toString()
            openNaverMap(address)
        }


    }

    private fun initBookmarkUI() {
        binding.ivSearchDetailBookmark.setOnClickListener {
            isBookmarked = !isBookmarked
            viewModel.setInterestReportAnimal(cardId)
            updateBookmarkUI(isBookmarked)
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
            else R.drawable.ic_search_detail_blank_bookmark
        )
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        binding.mapView.onStop()
        super.onStop()
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroyView() {
        binding.mapView.onDestroy()
        super.onDestroyView()
    }


}