package com.example.findu.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.example.findu.domain.usecase.DummyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dummyDataUseCase: DummyUseCase
) : ViewModel() {
}