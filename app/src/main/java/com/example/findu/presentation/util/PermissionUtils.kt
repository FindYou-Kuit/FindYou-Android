package com.example.findu.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtils {
    private const val CAMERA_PERMISSION = Manifest.permission.CAMERA

    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

}