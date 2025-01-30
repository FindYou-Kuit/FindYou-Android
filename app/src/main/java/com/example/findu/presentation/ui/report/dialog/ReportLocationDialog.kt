package com.example.findu.presentation.ui.report.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.findu.databinding.DialogReportLocationBinding
import com.example.findu.presentation.ui.report.ReportLocationActivity
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class ReportLocationDialog(
    private val address: String,
    private val onSetClickListener: (String) -> Unit = {},
) : DialogFragment(), OnMapReadyCallback {

    private val binding by lazy { DialogReportLocationBinding.inflate(layoutInflater) }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setUpMapView()
        setUpLocationTextView()
        setUpListener()

        return binding.root
    }

    private fun setUpLocationTextView() {

        with(binding.tvReportLocationDialogAddress) {
            text = address
            paintFlags = Paint.UNDERLINE_TEXT_FLAG

            resultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == AppCompatActivity.RESULT_OK) {
                        val data = result.data?.getStringExtra(POST_TAG)
                        binding.tvReportLocationDialogAddress.text = data // 결과를 TextView에 표시
                    }
                }
        }

        with(binding.llReportLocationDialogLocation) {
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
    }

    private fun setUpListener() {
        binding.btnReportLocationDialogSet.setOnClickListener {
            val newAddress = binding.tvReportLocationDialogAddress.text.toString()
            onSetClickListener(newAddress)
            dismiss()
        }

        binding.ivReportLocationDialogClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        with(naverMap.uiSettings) {
            isZoomControlEnabled = false
            isScaleBarEnabled = false
            isLocationButtonEnabled = true
        }
    }

    companion object {
        const val POST_TAG = "postData"
    }

}