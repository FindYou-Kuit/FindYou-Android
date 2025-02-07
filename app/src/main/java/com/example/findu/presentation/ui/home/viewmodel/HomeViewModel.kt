package com.example.findu.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.usecase.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: GetHomeUseCase
) : ViewModel() {
    private val _homeData = MutableStateFlow<HomeData?>(null)
    val homeData = _homeData.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)  // 오류 메시지 관리
    val errorMessage = _errorMessage.asStateFlow()

    fun getHomeData() {
        viewModelScope.launch {
            homeUseCase().fold(
                onSuccess = { data ->
                    _homeData.value = data
                },
                onFailure = { error ->
                    _errorMessage.value = error.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }
}