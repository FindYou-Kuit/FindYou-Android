package com.example.findu.presentation.ui.report

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.usecase.report.AnalysisImageWithGptUseCase
import com.example.findu.presentation.util.UriUtil.uriToBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val analysisImageWithGptUseCase: AnalysisImageWithGptUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _gptData: MutableStateFlow<GptData> = MutableStateFlow(GptData())
    val gptData = _gptData

    private val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage = _errorMessage

    fun getGptData(imageUri: Uri) {
        viewModelScope.launch {
            imageUri.uriToBase64(context)?.let { encodeString ->
                analysisImageWithGptUseCase(encodeString).fold(
                    onSuccess = { value ->
                        _gptData.value = value
                    },
                    onFailure = { exception ->
                        _errorMessage.value = exception.message.toString()
                    })
            } ?: run {
                _errorMessage.value = "Failed to convert image to base64"
            }
        }
    }
}