package com.kuit.findu.presentation.ui.my.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.findu.domain.usecase.PostInquiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
    private val postInquiryUseCase: PostInquiryUseCase
) : ViewModel() {

    private val _postInquirySuccess = MutableStateFlow<Boolean?>(null)
    val postInquirySuccess: StateFlow<Boolean?> = _postInquirySuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    fun postInquiry(title: String, content: String, categories: List<String>) {
        viewModelScope.launch {
            _loadingState.value = true

            postInquiryUseCase(title, content, categories)
                .onSuccess {
                    _postInquirySuccess.value = true
                }
                .onFailure { e ->
                    _postInquirySuccess.value = false
                    _errorMessage.value = e.message ?: "문의 전송 중 오류가 발생했습니다."
                }

            _loadingState.value = false
        }
    }

    fun resetInquiryState() {
        _postInquirySuccess.value = null
        _errorMessage.value = null
        _loadingState.value = false
    }
}