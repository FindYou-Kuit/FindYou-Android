package com.example.findu.presentation.ui.report

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.findu.domain.usecase.DummyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val dummyDataUseCase: DummyUseCase
) : ViewModel() {

    private val _imageUriList: MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    val imageUriList: StateFlow<List<Uri>> get() = _imageUriList

    fun addImageUri(uri: Uri) {
        val list = _imageUriList.value.toMutableList()
        list.add(uri)
        _imageUriList.value = list
    }

}