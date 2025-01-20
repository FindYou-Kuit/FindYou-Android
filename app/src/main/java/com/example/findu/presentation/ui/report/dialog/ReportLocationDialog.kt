package com.example.findu.presentation.ui.report.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SearchEvent
import com.example.findu.databinding.DialogReportLocationBinding
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class ReportLocationDialog(
    context: Context
) : Dialog(context), OnMapReadyCallback {

    private var _binding: DialogReportLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogReportLocationBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initMapView()
        initListener()
    }

    private fun initMapView() {

        val mapView = binding.mvReportLocationDialogMap
        mapView.getMapAsync(this)
    }

    private fun initListener() {
        binding.ivReportLocationDialogLocation.setOnClickListener { }

        binding.btnReportLocationDialogSet.setOnClickListener { }
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        with(naverMap.uiSettings) {
            isZoomControlEnabled = false
            isScaleBarEnabled = false
            isLocationButtonEnabled = true
        }
    }
}