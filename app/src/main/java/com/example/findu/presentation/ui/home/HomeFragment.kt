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
import androidx.navigation.fragment.findNavController
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
import com.example.findu.presentation.ui.search.SearchDisappearDetailFragment
import com.example.findu.presentation.ui.search.SearchProtectingDetailFragment
import com.example.findu.presentation.ui.search.SearchWitnessDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    private val homeBannerImages = listOf(
        R.drawable.img_adopt_info,
        R.drawable.img_volunteer_info,
        R.drawable.img_report_info
    )

    private val homeBannerTexts = listOf(
        R.string.home_banner_adopt,
        R.string.home_banner_volunteer,
        R.string.home_banner_report
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
            val dialog = HomeReportDialog(requireContext(), findNavController())
            dialog.show()
        }
    }

    private fun setupRV(homeData: HomeData) {
        lateinit var homeProtectAdapter: HomeRVAdapter
        lateinit var homeMissingAdapter: HomeRVAdapter
        val homeProtectList = homeData.protectAnimalCards.map {
            HomeRv(
                imageUrl = it.thumbnailImageUrl,
                name = it.title,
                type = AnimalStateType.fromTag(it.tag).state,
                date = it.noticeStartDate,
                location = it.careAddress,
                id = it.protectId
            )
        }

        val homeMissingList = homeData.reportAnimalCards.map {
            HomeRv(
                imageUrl = it.thumbnailImageUrl,
                name = it.title,
                type = AnimalStateType.fromTag(it.tag).state,
                date = it.registerDate,
                location = it.happenLocation,
                id = it.reportId
            )
        }

        homeProtectAdapter = HomeRVAdapter(homeProtectList) { item ->
            navigateToDetail(item)
        }
        homeMissingAdapter = HomeRVAdapter(homeMissingList) { item ->
            navigateToDetail(item)
        }

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

    private fun navigateToDetail(item: HomeRv) {
        val fragment = when (item.type) {
            "보호중" -> SearchProtectingDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("cardId", item.id.toLong())
                    putString("tag", item.type)
                    putString("name", item.name)
                }
            }

            "목격신고" -> SearchWitnessDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("cardId", item.id.toLong())
                    putString("tag", item.type)
                    putString("name", item.name)
                }
            }

            "실종신고" -> SearchDisappearDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("cardId", item.id.toLong())
                    putString("tag", item.type)
                    putString("name", item.name)
                }
            }

            else -> return
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .addToBackStack(null)
            .commit()
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

        binding.tvHomeBanner.text = getString(homeBannerTexts[0])

        binding.vpHomeBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val currentPage = (position % homeBannerImages.size) + 1
                val totalPages = homeBannerImages.size

                binding.tvHomeBanner.text = getString(homeBannerTexts[currentPage - 1])
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