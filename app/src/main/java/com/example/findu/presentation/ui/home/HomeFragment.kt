package com.example.findu.presentation.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.findu.R
import com.example.findu.databinding.FragmentHomeBinding
import com.example.findu.presentation.model.HomeRv
import com.example.findu.presentation.type.AnimalStateType
import com.example.findu.presentation.ui.home.adapter.HomeBannerAdapter
import com.example.findu.presentation.ui.home.adapter.HomeRVAdapter
import com.example.findu.presentation.ui.home.dialog.HomeFindDialog
import com.example.findu.presentation.ui.home.dialog.HomeReportDialog
import com.example.findu.presentation.ui.home.viewmodel.HomeViewModel

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

        setupTodayData()
        setupBanner()
        setupRV()
        setupReportDialog()
        setupFindDialog()

        return binding.root
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

    private fun setupRV() {
        lateinit var homeProtectAdapter: HomeRVAdapter
        lateinit var homeMissingAdapter: HomeRVAdapter
        val homeProtectList: List<HomeRv> = listOf( // dummy 값
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "말티즈",
                type = AnimalStateType.Protect.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "사모예드",
                type = AnimalStateType.Protect.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 자양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "푸들",
                type = AnimalStateType.Protect.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            )
        )

        val homeMissingList: List<HomeRv> = listOf( // dummy 값
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "말티즈",
                type = AnimalStateType.Find.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "사모예드",
                type = AnimalStateType.Missing.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 자양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "푸들",
                type = AnimalStateType.Find.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            )
        )

        homeProtectAdapter = HomeRVAdapter(homeProtectList)
        homeMissingAdapter = HomeRVAdapter(homeMissingList)
        binding.rvHomeProtect.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHomeMissing.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHomeProtect.adapter = homeProtectAdapter
        binding.rvHomeMissing.adapter = homeMissingAdapter
        val size = resources.getDimensionPixelSize(R.dimen.MY_SIZE)
        val m_size = resources.getDimensionPixelSize(R.dimen.MY_EDGE_MARGIN)
        val deco = SpaceDecoration(size, m_size)
        binding.rvHomeProtect.addItemDecoration(deco)
        binding.rvHomeMissing.addItemDecoration(deco)
    }

    private fun setupTodayData() {
        val rescueCount = 2 // 오늘 구조된 동물 dummy 값
        binding.tvHomeTodayRescueNum.text =
            getString(R.string.home_today_bar_rescue_num, rescueCount)

        val reportCount = 6 // 오늘 신고된 동물 dummy 값
        binding.tvHomeTodayReportNum.text =
            getString(R.string.home_today_bar_report_num, reportCount)
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