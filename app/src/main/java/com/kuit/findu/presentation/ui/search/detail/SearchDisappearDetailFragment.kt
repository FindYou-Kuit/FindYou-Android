package com.kuit.findu.presentation.ui.search.detail

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.MarginPageTransformer
import com.kuit.findu.R
import com.kuit.findu.data.mapper.todomain.toDetailSearchRvTag
import com.kuit.findu.data.mapper.todomain.toDetailSearchStatus
import com.kuit.findu.databinding.FragmentSearchDetailDisappearBinding
import com.kuit.findu.domain.model.search.DetailMissingData
import com.kuit.findu.presentation.ui.search.adapter.SearchDetailVPAdapter
import com.kuit.findu.presentation.ui.search.viewmodel.DetailSearchViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchDisappearDetailFragment : Fragment() {

    private lateinit var binding: FragmentSearchDetailDisappearBinding
    private val viewModel by viewModels<DetailSearchViewModel>()
    private var cardId: Long = -1
    private var tag: String? = null
    private var name: String? = null

    private val args: SearchDisappearDetailFragmentArgs by navArgs()
    private var isBookmarked = false

    private var naverMap: NaverMap? = null
    private var pendingLocation: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchDetailDisappearBinding.inflate(inflater, container, false)
        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync { nMap ->
            naverMap = nMap
            pendingLocation?.let { location ->
                setupMap(location.latitude, location.longitude)
            }
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
        initListener()
    }

    private fun setupMap(lat: Double, lon: Double) {
        val location = LatLng(lat, lon)
        val map = naverMap
        if (map == null) {
            pendingLocation = location
            return
        }
        pendingLocation = null
        map.moveCamera(CameraUpdate.scrollTo(location))
        Marker().apply {
            position = location
            this.map = map
            icon = OverlayImage.fromResource(R.drawable.ic_search_map_marker)
            height = 23
        }
    }


    private fun fetchDetailData() {
        when (tag) {
            "실종신고" -> viewModel.getDetailSearchMissing(cardId)
            else -> {
                Toast.makeText(requireContext(), "잘못된 태그 값입니다.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.detailMissingData.collectLatest { data ->
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

    private fun updateUI(data:DetailMissingData) {
        binding.apply {
            tvDetailTitleField.text = data.breed
            tvValueAge.text = data.age
            tvValueGender.text = data.sex
            tvValueLostDate.text = data.missingDate
            tvValueRfid.text = data.rfid
            tvSpecialNote.text = data.significant
            tvValueLostLocationAround.text = data.missingLocation
            tvValueLostAddress.text = data.missingAddress
            tvValueReporterName.text = data.reporterName
            tvValuePhoneNumber.text = data.reporterTel

            initTagView(data.tag)
            if (data.imageUrls.isNotEmpty()) {
                initViewPager(data.imageUrls)
            }
            setupMap(data.latitude, data.longitude)
        }
    }

    private fun initViewPager(imageList: List<String>) {
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

        ivSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        initBookmarkUI()

        llSendMessage.setOnClickListener {
            Toast.makeText(requireContext(), "준비 중이에요!", Toast.LENGTH_SHORT).show()
        }

        llViewMap.setOnClickListener {
            val address = binding.tvValueLostAddress.text.toString()
            openNaverMap(address)
        }

        clLostLocationCopy.setOnClickListener {
            val address = tvValueLostAddress.text.toString()
            if (address.isNotBlank()) {
                copyToClipboard(address)
                Toast.makeText(requireContext(), "주소가 복사되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "복사할 주소가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun copyToClipboard(text: String) {
        val clipboardManager =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun initBookmarkUI() {
        binding.ivSearchDetailBookmark.setOnClickListener {
            viewModel.toggleInterestMissing(cardId)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isInterested.collectLatest { interested ->
                updateBookmarkUI(interested)
                isBookmarked = interested
            }
        }
    }


    private fun initTagView(tag : String) {
        val status = tag.toDetailSearchStatus()
        val tagInfo = status.toDetailSearchRvTag()

        binding.tvDetailTagField.text = tag
        binding.tvDetailTagField.setTextColor(requireContext().getColor(tagInfo.textColor))
        binding.tvDetailTagField.setBackgroundResource(tagInfo.backgroundRes)
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