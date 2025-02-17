package com.example.findu.presentation.ui.report.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.report.GetAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase
) : ViewModel() {

    private val _location = MutableStateFlow<String?>(null)
    val location = _location.asStateFlow()

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun getLocation(
        lat: Double,
        lng: Double
    ) {
        viewModelScope.launch {
            getAddressUseCase(lat, lng).fold(
                onSuccess = { addressData ->
                    _location.value = addressData.address
                },
                onFailure = { error ->
                    Log.e("ReportLocationDialog", "getLocation: ${error.message}")
                    _errorMessage.value = error.message ?: "주소를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }
}