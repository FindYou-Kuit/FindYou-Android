package com.example.findu.presentation.ui.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.my.GetInterestUseCase
import com.example.findu.presentation.mapper.torvmodel.toRvModel
import com.example.findu.presentation.model.MyInterestRv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getInterestUseCase: GetInterestUseCase
) : ViewModel() {

    private val _interestAnimals = MutableStateFlow<List<MyInterestRv>>(emptyList())
    val interestAnimals = _interestAnimals.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun fetchInterestAnimals() {
        viewModelScope.launch {
            getInterestUseCase(
                lastReportId = Long.MAX_VALUE,
                lastProtectId = Long.MAX_VALUE
            ).fold(
                onSuccess = { data ->
                    _interestAnimals.value = data.interestAnimals.map { it.toRvModel() }
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

}