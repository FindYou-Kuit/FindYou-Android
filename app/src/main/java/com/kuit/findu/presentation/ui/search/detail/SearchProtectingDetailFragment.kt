package com.kuit.findu.presentation.ui.search.detail

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.viewpager2.widget.MarginPageTransformer
import com.kuit.findu.R
import com.kuit.findu.data.mapper.todomain.toDetailSearchRvTag
import com.kuit.findu.data.mapper.todomain.toDetailSearchStatus
import com.kuit.findu.databinding.FragmentSearchDetailProtectingBinding
import com.kuit.findu.domain.model.search.DetailProtectData
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
class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchDetailProtectingBinding
    private val viewModel by viewModels<DetailSearchViewModel>()
    private var cardId: Long = -1
    private var tag: String? = null
    private var name: String? = null

    private val args: SearchProtectingDetailFragmentArgs by navArgs()

    private var isBookmarked = false

    private var naverMap: NaverMap? = null
    private var pendingLocation: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchDetailProtectingBinding.inflate(layoutInflater)
        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync { nMap ->
            naverMap = nMap
            naverMap?.uiSettings?.isLogoClickEnabled = false
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
        if (map == null){
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
            "보호중" -> viewModel.getDetailSearchProtect(cardId)
            else -> {
                Toast.makeText(requireContext(), "잘못된 태그 값입니다.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.detailProtectData.collectLatest { data ->
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
            tvValueName.text = data.breed
            tvValueAge.text = data.age
            tvValueWeight.text = data.weight
            tvValueGender.text = data.sex
            tvValueNeuter.text = data.noticeNumber
            tvValueFoundDate.text = data.foundDate
            tvValueHairColor.text = data.furColor
            tvSpecialNote.text = data.significant
            tvShelterLocation.text = data.careAddr
            tvValueShelterName.text = data.careName
            tvValueNotiDate.text = data.noticeDuration
            tvValueNotiNum.text = data.noticeNumber
            tvValueShelterPhoneNumber.text = data.careTel
            tvValueJurisdiction.text = data.authority
            tvValueProtectLocation.text = data.foundLocation.ifBlank { data.careAddr }

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

        llCallPhone.setOnClickListener {
            val phoneNumber = binding.tvValueShelterPhoneNumber.text.toString()
            call(phoneNumber)
        }

        llViewMap.setOnClickListener {
            val address = binding.tvValueProtectLocation.text.toString()
            openNaverMap(address)
        }
        clProtectLocationCopy.setOnClickListener {
            val address = tvValueProtectLocation.text.toString()
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


    private fun call(phoneNumber: String) {
        if (phoneNumber.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        }
    }

    private fun initBookmarkUI() {
        binding.ivSearchDetailBookmark.setOnClickListener {
            viewModel.toggleInterestProtect(cardId)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isInterested.collectLatest { interested ->
                updateBookmarkUI(interested)
                isBookmarked = interested
            }
        }
    }

    private fun initTagView(tag: String) {
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