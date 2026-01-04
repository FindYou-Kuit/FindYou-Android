package com.kuit.findu.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kuit.findu.R
import com.kuit.findu.analytics.AnalyticsHelper
import com.kuit.findu.analytics.logScreenView
import com.kuit.findu.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMain.setupWithNavController(navController)
        setBottomNaviVisible(navController)
    }

    private fun setBottomNaviVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val screenName = try {
                resources.getResourceEntryName(destination.id)
            } catch (_: Exception) {
                "unknown_screen"
            }

            analyticsHelper.logScreenView(screenName)

            binding.bnvMain.visibility = when (destination.id) {
                R.id.fragment_home, R.id.fragment_search, R.id.fragment_info, R.id.fragment_my -> View.VISIBLE
                else -> View.GONE
            }
        }
    }
}