package com.example.findu.presentation.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

object UriUtil {
    private const val BASE64_PREFIX = "data:image/jpeg;base64,"

    fun Uri.uriToBase64(context: Context): String {
        return try {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(this)

            inputStream?.use {
                val bitmap: Bitmap = BitmapFactory.decodeStream(it)

                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val byteArray: ByteArray = outputStream.toByteArray()
                outputStream.close()

                Base64.encodeToString(byteArray, Base64.NO_WRAP)
            } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val fileName = getFileName(context, uri)

        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.compressAndSave(context, uri, file)
        return File(file.absolutePath)
    }

    private fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)!!.split("/").last()

        return "$name.$ext"
    }

    fun List<Uri>.toMultiPartBodys(context: Context): List<MultipartBody.Part> {
        if (this.isEmpty()) return emptyList()

        return this.map {
            val file = uriToFile(it, context)
            val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files", file.name, image)
        }
    }

    fun Uri.toSingleImageFile(context: Context): File {
        val fileName = getFileName(context, this)
        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.compressAndSave(context, this, file)
        return file
    }
}
