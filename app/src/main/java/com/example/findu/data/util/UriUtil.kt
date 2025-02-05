package com.example.findu.data.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Base64
import androidx.core.content.res.ResourcesCompat
import java.io.ByteArrayOutputStream
import java.io.InputStream

object UriUtil {
    fun Uri.uriToBase64(context: Context): String? {
        return try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(this)

            inputStream?.use {
                // URI를 Bitmap으로 변환
                val bitmap: Bitmap = BitmapFactory.decodeStream(it)

                // Bitmap을 ByteArray로 변환
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val byteArray: ByteArray = outputStream.toByteArray()
                outputStream.close()

                // ByteArray를 Base64로 변환
                "\"data:image/jpeg;base64,${Base64.encodeToString(byteArray, Base64.NO_WRAP)}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
