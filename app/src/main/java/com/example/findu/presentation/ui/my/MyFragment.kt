package com.example.findu.presentation.ui.my

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.findu.R
import com.example.findu.databinding.FragmentMyBinding
import com.example.findu.presentation.ui.my.dialog.MyWithdrawalDialog
import com.example.findu.presentation.util.PermissionUtils.hasCameraPermission
import com.example.findu.presentation.util.PermissionUtils.hasLocationPermission
import com.example.findu.presentation.util.PermissionUtils.requestLocationPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)


        initListener()

        return binding.root
    }

    private fun initListener() {

        with(binding) {
            llMyNickname.setOnClickListener {
                llMyNickname.visibility = View.INVISIBLE
                llMyEditNickname.visibility = View.VISIBLE
            }

            btnMyDoneEdit.setOnClickListener {
                llMyNickname.visibility = View.VISIBLE
                llMyEditNickname.visibility = View.INVISIBLE

                myViewModel.updateNickName(etMyNickname.text.toString())
            }

            etMyNickname.addTextChangedListener { text ->
                tvMyNickname.text = text.toString()
            }

            btnMyReportHistory.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_my_to_fragment_my_report_history)
            }

            clMyRecentHistory.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_my_to_fragment_my_recent_history)
            }

            clMyKeepAnimal.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_my_to_fragment_my_keep_animals)
            }

            clMyCameraPermission.setOnClickListener {
                if (hasCameraPermission(requireContext())) {
                    Toast.makeText(requireContext(), "카메라 권한이 이미 허용되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    launchCameraRequestPermission()

                }
            }

            clMyLocationPermission.setOnClickListener {
                if (hasLocationPermission(requireContext())) {
                    Toast.makeText(requireContext(), "위치 권한이 이미 허용되었습니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    requestLocationPermission(requireActivity())
                }
            }

            clMyWithdrawal.setOnClickListener {
                MyWithdrawalDialog(
                    context = requireContext(),
                    onWithdrawalClick = { myViewModel.deleteUserData() }).show()
            }
        }
    }

    private fun launchCameraRequestPermission() {
        val requestPermissionLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    Toast.makeText(requireContext(), "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().popBackStack()
                }

            }
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycle.currentState) {
                launch {
                    myViewModel.deleteUserMessage.collect { message ->
                        message?.let {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                launch {
                    myViewModel.errorMessage.collect { message ->
                        message?.let {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                launch {
                    myViewModel.nickNameState.collect { nickName ->
                        nickName?.let {
                            binding.tvMyNickname.text = nickName
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}