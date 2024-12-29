package com.example.findu.presentation.ui.report

import androidx.lifecycle.ViewModel
import com.example.findu.domain.usecase.DummyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val dummyDataUseCase: DummyUseCase
) : ViewModel() {
}