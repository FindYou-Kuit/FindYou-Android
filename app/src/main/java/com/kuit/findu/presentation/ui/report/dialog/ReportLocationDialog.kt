package com.kuit.findu.presentation.ui.report.dialog

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kuit.findu.BuildConfig
import com.kuit.findu.R
import com.kuit.findu.databinding.DialogReportLocationBinding
import com.kuit.findu.presentation.ui.report.viewmodel.LocationViewModel
import com.kuit.findu.presentation.util.PermissionUtils.REQUEST_CODE_LOCATION_PERMISSION
import com.kuit.findu.presentation.util.PermissionUtils.hasLocationPermission
import com.kuit.findu.presentation.util.PermissionUtils.requestLocationPermission
import com.kuit.findu.presentation.util.ViewUtils.addUnderLine
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationSource
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportLocationDialog(
    private val address: String,
    private val onSetClickListener: (String) -> Unit = {},
) : DialogFragment(), OnMapReadyCallback {

    private val binding by lazy { DialogReportLocationBinding.inflate(layoutInflater) }
    private val locationViewModel by viewModels<LocationViewModel>()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var naverMap: NaverMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationSource: LocationSource

    private var cameraMoveCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)


        NaverMapSdk.getInstance(requireContext()).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_ID)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (!hasLocationPermission(requireContext())) {
            requestLocationPermission(requireActivity())
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        }

        setUpMapView()

        setUpLocationTextView()
        setUpListener()

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycle.currentState) {
                launch {
                    locationViewModel.location.collectLatest { location ->
                        location?.let {
                            binding.tvReportLocationDialogAddress.text = it
                        } ?: run {
                            binding.tvReportLocationDialogAddress.setText(R.string.report_location)
                        }
                    }
                    launch {
                        locationViewModel.errorMessage.collectLatest { errorMessage ->
                            errorMessage?.let {
                                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpLocationTextView() {

        with(binding.tvReportLocationDialogAddress) {
            text = address
            addUnderLine()

            resultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == AppCompatActivity.RESULT_OK) {
                        val data = result.data?.getStringExtra(POST_TAG)
                        binding.tvReportLocationDialogAddress.text = data // 결과를 TextView에 표시
                    }
                }
        }

        with(binding.clReportLocationDialogLocation) {
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT

            setOnClickListener {
                val intent = Intent(context, ReportLocationActivity::class.java)
                resultLauncher.launch(intent)
            }
        }

    }


    private fun setUpMapView() {
        val mapView = binding.mvReportLocationDialogMap
        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, REQUEST_CODE_LOCATION_PERMISSION)
    }

    private fun setUpListener() {
        binding.btnReportLocationDialogSet.setOnClickListener {
            val newAddress = binding.tvReportLocationDialogAddress.text.toString()
            onSetClickListener(newAddress)
            dismiss()
        }

        binding.clReportLocationDialogClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        with(naverMap) {
            this.locationSource = locationSource
            with(uiSettings) {
                isZoomControlEnabled = false
                isScaleBarEnabled = false
            }
            addOnCameraIdleListener {
                if (cameraMoveCount < 1) {
                    cameraMoveCount += 1

                } else {
                    val latLng = cameraPosition.target
                    locationViewModel.getLocation(latLng.latitude, latLng.longitude)
                }
            }

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }


            if (hasLocationPermission(requireContext())) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        moveCamera(
                            CameraUpdate.scrollTo(
                                LatLng(
                                    location?.latitude ?: 37.5666102,
                                    location?.longitude ?: 126.9783881
                                )
                            )
                        )
                    }
            }

        }
    }

    companion object {
        const val POST_TAG = "postData"
    }

}