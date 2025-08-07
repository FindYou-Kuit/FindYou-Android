package com.example.findu.presentation.ui.my.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import com.example.findu.R
import com.example.findu.databinding.DialogMyProfileImageBinding

class MyProfileImageDialog(
    context: Context,
    private val onDrawableSelected: (Int) -> Unit,
    private val onGallerySelected: (Uri) -> Unit,
    private val launchGallery: () -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogMyProfileImageBinding
    private var selectedUri: Uri? = null
    private var selectedImageResId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogMyProfileImageBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        initListeners()
    }

    private fun initListeners() = with(binding) {
        ivDialogClose.setOnClickListener { dismiss() }

        binding.ivMyIllust.setOnClickListener {
            launchGallery()
        }

        ivProfileImgDafult.setOnClickListener {
            selectedImageResId = R.drawable.img_my_profile_default
            selectedUri = null
            binding.ivMyIllust.setImageResource(selectedImageResId)
        }
        ivProfileImg1.setOnClickListener {
            selectedImageResId = R.drawable.img_my_profile1
            selectedUri = null
            binding.ivMyIllust.setImageResource(selectedImageResId)
        }
        ivProfileImg2.setOnClickListener {
            selectedImageResId = R.drawable.img_my_profile2
            selectedUri = null
            binding.ivMyIllust.setImageResource(selectedImageResId)
        }
        ivProfileImg3.setOnClickListener {
            selectedImageResId = R.drawable.img_my_profile3
            selectedUri = null
            binding.ivMyIllust.setImageResource(selectedImageResId)
        }

        binding.btnChangeProfileImage.setOnClickListener {
            when{
                selectedUri != null ->onGallerySelected(selectedUri!!)
                selectedImageResId != 0 -> onDrawableSelected(selectedImageResId)
            }

            dismiss()
        }

    }

    fun setGalleryImage(uri: Uri) {
        selectedUri = uri
        selectedImageResId = 0
        binding.ivMyIllust.setImageURI(uri)
    }
}