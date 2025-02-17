package com.example.findu.domain.repository.report

import android.net.Uri
import com.example.findu.domain.model.report.GptData
import okhttp3.MultipartBody

interface ReportRepository {
    suspend fun postImageAnalysis(encodeString: String) : Result<GptData>

    suspend fun uploadImages(images: List<MultipartBody.Part>) : Result<List<Int>>
}