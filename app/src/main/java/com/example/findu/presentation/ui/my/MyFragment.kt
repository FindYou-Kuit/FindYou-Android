package com.example.findu.presentation.ui.my

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.findu.BuildConfig
import com.example.findu.R
import com.example.findu.databinding.FragmentMyBinding
import com.example.findu.presentation.ui.login.LoginActivity
import com.example.findu.presentation.ui.my.dialog.MyLogoutDialog
import com.example.findu.presentation.ui.my.dialog.MyNicknameDialog
import com.example.findu.presentation.ui.my.dialog.MyProfileImageDialog
import com.example.findu.presentation.ui.my.dialog.MyWithdrawalDialog
import com.example.findu.presentation.ui.my.model.ProfileImageType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private var myProfileImageDialog: MyProfileImageDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            if (uri != null) {
                myProfileImageDialog?.setGalleryImage(uri)
            } else {
                Toast.makeText(requireContext(), "이미지가 선택되지 않았어요.", Toast.LENGTH_SHORT).show()
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
        myViewModel.fetchMyProfile()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
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

            clMyProfileImage.setOnClickListener {
                myProfileImageDialog = MyProfileImageDialog(
                    context = requireContext(),
                    onDrawableSelected = { resId ->
                        val defaultName = when (resId) {
                            R.drawable.img_my_profile_default -> "default"
                            R.drawable.img_my_profile1 -> "puppy"
                            R.drawable.img_my_profile2 -> "chick"
                            R.drawable.img_my_profile3 -> "panda"
                            else -> "default"
                        }
                        myViewModel.updateProfileImage(enumName = defaultName)
                    },
                    onGallerySelected = { uri ->
                        myViewModel.updateProfileImageFromGallery(
                            context = requireContext(),
                            uri = uri
                        )
                    },
                    launchGallery = {
                        pickMedia.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
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

            clMyLogout.setOnClickListener(logoutClickListener)
            tvMyVersionInfo.setOnClickListener(logoutClickListener)
            chipMyVersion.setOnClickListener(logoutClickListener)

            clMyGotoUpdate.setOnClickListener {
                val pkg = requireContext().packageName
                try {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkg"))
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$pkg")
                        )
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

            clMyAlarmSetting.setOnClickListener {
                myViewModel.toggleAlarmSetting()
            }

            setupVersion()
        }
    }

    private fun setupVersion() = with(binding) {
        val currentVersion = BuildConfig.VERSION_NAME
        val latest = "1.0"
        tvMyVersionInfo.text = "버전 정보 $currentVersion"

        val currentNumber = currentVersion.substringBefore("-")
            .replace(".", "")
            .toIntOrNull() ?: 0

        val latestNumber = latest.replace(".", "").toIntOrNull() ?: 0

        val isLatest = currentNumber >= latestNumber

        clMyVersionChip.isVisible = isLatest
        clMyGotoUpdate.isVisible = !isLatest
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    myViewModel.myProfile.collect { profile ->
                        profile?.let {
                            binding.tvMyNickname.text = it.nickname
                            binding.etMyNickname.setText(it.nickname)

                            val imageSource = it.profileImage
                            if (imageSource.startsWith("http")) {
                                Glide.with(this@MyFragment)
                                    .load(imageSource)
                                    .into(binding.ivMyIllust)
                            } else {
                                val type = ProfileImageType.fromServerName(imageSource)
                                Glide.with(this@MyFragment)
                                    .load(type.drawableRes)
                                    .into(binding.ivMyIllust)
                            }
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
                launch {
                    myViewModel.alarmEnabled.collect { enabled ->
                        binding.ivMyAlarmIcon.setImageResource(
                            if (enabled) R.drawable.img_my_alarm_on else R.drawable.img_my_alarm_off
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myProfileImageDialog = null
        _binding = null
    }
}