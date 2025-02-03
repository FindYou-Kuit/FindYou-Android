package com.example.findu.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    private const val CAMERA_PERMISSION = Manifest.permission.CAMERA

    fun requestPermission(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(CAMERA_PERMISSION),
            0
        )
    }

    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

}