package com.example.findu.presentation.ui.main

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.findu.R
import com.example.findu.databinding.ActivityMainBinding
import com.example.findu.domain.usecase.SetDeviceIdUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var setDeviceIdUseCase: SetDeviceIdUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            ?: "unknown_device_id"

        setDeviceIdUseCase(deviceId=deviceId)


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
            binding.bnvMain.visibility = when (destination.id) {
                R.id.fragment_home, R.id.fragment_search, R.id.fragment_info, R.id.fragment_my -> View.VISIBLE
                R.id.fragment_search_detail_witness, R.id.fragment_search_detail_disappear, R.id.fragment_search_detail_protecting -> View.VISIBLE
                else -> View.GONE
            }
        }
    }
}