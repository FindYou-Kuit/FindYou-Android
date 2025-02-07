package com.example.findu.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    const val REQUEST_CODE_LOCATION_PERMISSION = 1
    private val LOCATION_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, CAMERA_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

    /** @param activity */
    fun requestLocationPermission(
        activity: Activity
    ) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_LOCATION_PERMISSION
        )
    }


    /** @param context */
    fun hasLocationPermission(
        context: Context
    ): Boolean {
        for (permission in LOCATION_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }


}