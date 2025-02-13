package com.example.findu.presentation.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream

object UriUtil {
    private const val Base64_PREFIX = "data:image/jpeg;base64,"

    fun Uri.uriToBase64(context: Context): String? {
        return try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(this)

            inputStream?.use {
                val bitmap: Bitmap = BitmapFactory.decodeStream(it)

                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val byteArray: ByteArray = outputStream.toByteArray()
                outputStream.close()

                Base64_PREFIX + Base64.encodeToString(byteArray, Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
