package com.example.findu.presentation.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.findu.R
import com.example.findu.databinding.FragmentHomeBinding
import com.example.findu.domain.model.HomeData
import com.example.findu.presentation.model.HomeRv
import com.example.findu.presentation.type.AnimalStateType
import com.example.findu.presentation.ui.home.adapter.HomeBannerAdapter
import com.example.findu.presentation.ui.home.adapter.HomeRVAdapter
import com.example.findu.presentation.ui.home.dialog.HomeFindDialog
import com.example.findu.presentation.ui.home.dialog.HomeReportDialog
import com.example.findu.presentation.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    private val homeBannerImages = listOf(
        R.drawable.img_banner_green,
        R.drawable.img_banner_purple,
        R.drawable.img_banner_blue
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        observeViewModel()
        homeViewModel.getHomeData()

        setupBanner()
        setupReportDialog()
        setupFindDialog()

        return binding.root
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycle.currentState) {
                launch {
                    homeViewModel.homeData.collectLatest { homeData ->
                        homeData?.let {
                            setupTodayData(it)
                            setupRV(it)
                        }
                    }
                }

                launch {
                    homeViewModel.errorMessage.collectLatest { errorMessage ->
                        errorMessage?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupFindDialog() {
        binding.cvHomeFind.setOnClickListener {
            val dialog = HomeFindDialog(requireContext())
            dialog.show()
        }
    }

    private fun setupReportDialog() {
        binding.cvHomeReport.setOnClickListener {
            val dialog = HomeReportDialog(requireContext())
            dialog.show()
        }
    }

    private fun setupRV(homeData: HomeData) {
        lateinit var homeProtectAdapter: HomeRVAdapter
        lateinit var homeMissingAdapter: HomeRVAdapter
        val homeProtectList = homeData.protectAnimalCards.map {
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = it.title,
                type = AnimalStateType.fromTag(it.tag).state,
                date = it.noticeStartDate,
                location = it.careAddress
            )
        }

        val homeMissingList = homeData.reportAnimalCards.map {
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = it.title,
                type = AnimalStateType.fromTag(it.tag).state,
                date = it.registerDate,
                location = it.happenLocation
            )
        }

        homeProtectAdapter = HomeRVAdapter(homeProtectList)
        homeMissingAdapter = HomeRVAdapter(homeMissingList)

        binding.rvHomeProtect.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeProtectAdapter
        }

        binding.rvHomeMissing.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeMissingAdapter
        }

        val size = resources.getDimensionPixelSize(R.dimen.MY_SIZE)
        val m_size = resources.getDimensionPixelSize(R.dimen.MY_EDGE_MARGIN)
        val deco = SpaceDecoration(size, m_size)
        binding.rvHomeProtect.addItemDecoration(deco)
        binding.rvHomeMissing.addItemDecoration(deco)
    }

    private fun setupTodayData(homeData: HomeData) {
        binding.tvHomeTodayRescueNum.text =
            getString(R.string.home_today_bar_rescue_num, homeData.todayRescuedAnimalCount)

        binding.tvHomeTodayReportNum.text =
            getString(R.string.home_today_bar_report_num, homeData.todayReportAnimalCount)
    }

    private fun setupBanner() {
        val vpAdapter = HomeBannerAdapter(homeBannerImages)
        binding.vpHomeBanner.adapter = vpAdapter

        val startPosition = Int.MAX_VALUE / 2
        binding.vpHomeBanner.setCurrentItem(
            startPosition - (startPosition % homeBannerImages.size),
            false
        )

        binding.vpHomeBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val currentPage = (position % homeBannerImages.size) + 1
                val totalPages = homeBannerImages.size
                binding.tvHomeBannerNum.text = getString(
                    R.string.home_banner_num,
                    currentPage,
                    totalPages
                )
            }
        })

        val initialPage = (startPosition % homeBannerImages.size) + 1
        binding.tvHomeBannerNum.text = getString(
            R.string.home_banner_num,
            initialPage,
            homeBannerImages.size
        )

        val autoScrollHandler = Handler(Looper.getMainLooper())
        val autoScrollRunnable = object : Runnable {
            override fun run() {
                binding.vpHomeBanner.currentItem = binding.vpHomeBanner.currentItem + 1
                autoScrollHandler.postDelayed(this, 5000L)
            }
        }

        autoScrollHandler.postDelayed(autoScrollRunnable, 5000L)
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                autoScrollHandler.removeCallbacks(autoScrollRunnable)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}