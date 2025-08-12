package com.example.findu.presentation.ui.my

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.findu.BuildConfig
import com.example.findu.R
import com.example.findu.databinding.FragmentMyBinding
import com.example.findu.presentation.ui.login.LoginActivity
import com.example.findu.presentation.ui.my.dialog.MyLogoutDialog
import com.example.findu.presentation.ui.my.dialog.MyNicknameDialog
import com.example.findu.presentation.ui.my.dialog.MyProfileImageDialog
import com.example.findu.presentation.ui.my.dialog.MyWithdrawalDialog
import com.example.findu.presentation.util.PermissionUtils.hasLocationPermission
import com.example.findu.presentation.util.PermissionUtils.requestLocationPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var myProfileImageDialog: MyProfileImageDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    Toast.makeText(requireContext(), "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().popBackStack()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)

        initListener()
        myViewModel.fetchNickName()

        return binding.root
    }

    private fun initListener() {
        with(binding) {
            llMyNickname.setOnClickListener {
                MyNicknameDialog(
                    context = requireContext(),
                    onNicknameChange = { newNickname ->
                        myViewModel.updateNickName(newNickname)
                    }
                ).show()
            }

            galleryLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val uri = result.data?.data
                        uri?.let {
                            myProfileImageDialog?.setGalleryImage(it)
                        }
                    }
                }

            clMyProflieImage.setOnClickListener {
                myProfileImageDialog = MyProfileImageDialog(
                    context = requireContext(),
                    onDrawableSelected = { resId ->
                        myViewModel.updateProfileImage(resId)
                    },
                    onGallerySelected = { uri ->
                        myViewModel.updateProfileImageFromGallery(uri)
                    },
                    launchGallery = {
                        val intent = Intent(Intent.ACTION_PICK).apply {
                            type = "image/*"
                        }
                        galleryLauncher.launch(intent)
                    }
                )
                myProfileImageDialog?.show()
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

            clMyInquire.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_my_to_fragment_inquire)
            }

            val logoutClickListener = View.OnClickListener {
                MyLogoutDialog(
                    context = requireContext(),
                    onLogoutClick = {
                        with(requireActivity()) {
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                            finish()
                        }
                    }
                ).show()
            }

            binding.clMyLogout.setOnClickListener(logoutClickListener)
            binding.tvMyVersionInfo.setOnClickListener(logoutClickListener)
            binding.chipMyVersion.setOnClickListener(logoutClickListener)

            binding.clMyGotoUpdate.setOnClickListener{
                val pkg = requireContext().packageName
                try {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkg"))
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pkg"))
                    )
                }
            }

            clMyWithdrawal.setOnClickListener {
                MyWithdrawalDialog(
                    context = requireContext(),
                    onWithdrawalClick = {
                        myViewModel.deleteUserData()
                        with(requireActivity()) {
                            startActivity(
                                Intent(requireContext(), LoginActivity::class.java)
                            )
                            finish()
                        }
                    }).show()
            }

            val currentVersion = BuildConfig.VERSION_NAME
            binding.tvMyVersionInfo.text = "버전 정보 $currentVersion"
            val latest = "1.0"
            val isLatest = currentVersion.replace(".", "").toInt() >= latest.replace(".", "").toInt()
            binding.clMyVersionChip.isVisible = isLatest
            binding.clMyGotoUpdate.isVisible = !isLatest


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    myViewModel.nickNameState.collect { nickName ->
                        nickName?.let {
                            binding.tvMyNickname.text = nickName
                            binding.etMyNickname.setText(nickName)
                        }
                    }
                }
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
                    myViewModel.selectedImageResId.collect { resId ->
                        resId?.let {
                            binding.ivMyIllust.setImageResource(it)
                        }
                    }
                }
                launch {
                    myViewModel.selectedProfileImageUri.collect { uri ->
                        uri?.let {
                            binding.ivMyIllust.setImageURI(it)
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
