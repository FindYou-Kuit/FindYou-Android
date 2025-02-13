package com.example.findu.presentation.ui.report

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.findu.databinding.FragmentReportCameraBinding
import com.example.findu.presentation.ui.report.MissingReportFragment.Companion.IMAGE_RESULT_KEY
import com.example.findu.presentation.ui.report.MissingReportFragment.Companion.IMAGE_URI
import com.example.findu.presentation.util.PermissionUtils.hasCameraPermission
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class ReportCameraFragment : Fragment() {

    private var _binding: FragmentReportCameraBinding? = null
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null
    private var imageUri: Uri? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportCameraBinding.inflate(inflater, container, false)

        if (hasCameraPermission(requireContext())) {
            startCamera()
        } else {
            launchRequestPermission()
        }

        binding.btnReportCameraCaptureImage.setOnClickListener {
            takePhoto()
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        return binding.root
    }

    private fun launchRequestPermission() {
        val requestPermissionLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startCamera()
                } else {
                    findNavController().popBackStack()
                }

            }
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun takePhoto() {
        imageCapture ?: let {
            Toast.makeText(requireContext(), FAILED_TO_TAKE_PHOTO_MESSAGE, Toast.LENGTH_SHORT).show()
            return@takePhoto
        }

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, TYPE_IMAGE_JPEG)
            put(MediaStore.Images.Media.RELATIVE_PATH, PICTURE_ROUTE)
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireActivity()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        FAILED_TO_TAKE_PHOTO_MESSAGE,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    imageUri = output.savedUri
                    setFragmentResult(IMAGE_URI, bundleOf(IMAGE_RESULT_KEY to imageUri.toString()))
                    findNavController().navigateUp()
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.pvReportCameraViewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(requireContext(), FAILED_TO_START_CAMERA_MESSAGE, Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }

        }, ContextCompat.getMainExecutor(requireActivity()))
    }


    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
            ).apply {
            }.toTypedArray()

        private const val TYPE_IMAGE_JPEG = "image/jpeg"
        private const val PICTURE_ROUTE = "Pictures/FindU-Image"

        private const val FAILED_TO_TAKE_PHOTO_MESSAGE = "이미지 촬영에 실패했습니다."
        private const val FAILED_TO_START_CAMERA_MESSAGE = "카메라를 시작할 수 없습니다."
    }

}

