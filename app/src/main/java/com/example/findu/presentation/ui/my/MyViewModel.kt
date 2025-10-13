package com.example.findu.presentation.ui.my

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.usecase.interest.DeleteInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestProtectingAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestReportAnimalUseCase
import com.example.findu.domain.usecase.my.DeleteUserUseCase
import com.example.findu.domain.usecase.my.GetInterestUseCase
import com.example.findu.domain.usecase.my.GetNickNameUseCase
import com.example.findu.domain.usecase.my.GetReportHistoryUseCase
import com.example.findu.domain.usecase.my.GetViewedAnimalUseCase
import com.example.findu.domain.usecase.my.PatchNickNameUseCase
import com.example.findu.domain.usecase.report.DeleteReportUseCase
import com.example.findu.presentation.mapper.todomain.toRvModel
import com.example.findu.presentation.model.MyInterestRv
import com.example.findu.presentation.model.MyReportHistoryRv
import com.example.findu.presentation.model.MyViewedAnimalsRv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getInterestUseCase: GetInterestUseCase,
    private val getReportHistoryUseCase: GetReportHistoryUseCase,
    private val getViewedAnimalUseCase: GetViewedAnimalUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val patchNickNameUseCase: PatchNickNameUseCase,
    private val getNickNameUseCase: GetNickNameUseCase,
    private val postInterestProtectingAnimalUseCase: PostInterestProtectingAnimalUseCase,
    private val postInterestReportAnimalUseCase: PostInterestReportAnimalUseCase,
    private val deleteInterestProtectingAnimalUseCase: DeleteInterestProtectingAnimalUseCase,
    private val deleteInterestReportAnimalUseCase: PostInterestReportAnimalUseCase,
    private val deleteReportUseCase: DeleteReportUseCase,
) : ViewModel() {

    private val _interestAnimals = MutableStateFlow<List<MyInterestRv>>(emptyList())
    val interestAnimals = _interestAnimals.asStateFlow()

    private val _reportHistory = MutableStateFlow<List<MyReportHistoryRv>>(emptyList())
    val reportHistory = _reportHistory.asStateFlow()

    private val _viewedAnimals = MutableStateFlow<List<MyViewedAnimalsRv>>(emptyList())
    val viewedAnimals = _viewedAnimals.asStateFlow()

    private val _deleteUserMessage = MutableStateFlow<String?>(null)
    val deleteUserMessage = _deleteUserMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _nickNameState = MutableStateFlow<String?>(null)
    val nickNameState = _nickNameState.asStateFlow()

    private val _selectedImageResId = MutableStateFlow<Int?>(null)
    val selectedImageResId = _selectedImageResId.asStateFlow()

    private val _selectedProfileImageUri = MutableStateFlow<Uri?>(null)
    val selectedProfileImageUri: StateFlow<Uri?> = _selectedProfileImageUri

    private val _alarmEnabled = MutableStateFlow(false)
    val alarmEnabled : StateFlow<Boolean> = _alarmEnabled

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

    fun fetchReportHistory() {
        viewModelScope.launch {
            getReportHistoryUseCase(
                lastReportId = Long.MAX_VALUE,
            ).fold(
                onSuccess = { data ->
                    _reportHistory.value = data.reports.map { it.toRvModel() }
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun fetchViewedAnimals() {
        viewModelScope.launch {
            getViewedAnimalUseCase(
                lastReportId = Long.MAX_VALUE,
                lastProtectId = Long.MAX_VALUE
            ).fold(
                onSuccess = { data ->
                    _viewedAnimals.value = data.viewedAnimals.map { it.toRvModel() }
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "데이터를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun deleteUserData() {
        viewModelScope.launch {
            deleteUserUseCase().fold(
                onSuccess = {
                    _deleteUserMessage.value = "회원 탈퇴가 완료되었습니다."
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "회원 탈퇴 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun updateProfileImage(resId: Int) {
        _selectedImageResId.value = resId
    }


    fun updateProfileImageFromGallery(uri: Uri) {
        _selectedProfileImageUri.value = uri
    }

    fun toggleAlarmSetting(){
        _alarmEnabled.value = !_alarmEnabled.value
    }

    fun updateNickName(newNickName: String) {
        _nickNameState.value = newNickName

        viewModelScope.launch {
            patchNickNameUseCase(newNickName).fold(
                onSuccess = {},
                onFailure = {
                    _errorMessage.value = it.message ?: "닉네임 변경 중 오류가 발생했습니다."
                }
            )
        }
    }


    fun fetchNickName() {
        viewModelScope.launch {
            getNickNameUseCase().fold(
                onSuccess = {
                    _nickNameState.value = it
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "닉네임을 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun setInterest(
        id: Long,
        isInterest: Boolean,
        tag: String,
    ) {
        when (tag) {
            "보호중" -> postProtectInterest(id, isInterest)
            "목격신고" -> postReportInterest(id, isInterest)
            "실종신고" -> postReportInterest(id, isInterest)
            else -> {
                _errorMessage.value = "잘못된 태그 값입니다."
            }
        }

    }

    private fun postProtectInterest(id: Long, isInterest: Boolean) {
        viewModelScope.launch {
            if (isInterest) {
                postInterestProtectingAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 등록 중 오류가 발생했습니다."
                    }
                )
            } else {
                deleteInterestProtectingAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 해제 중 오류가 발생했습니다."
                    }
                )
            }
        }
    }

    private fun postReportInterest(id: Long, isInterest: Boolean) {
        viewModelScope.launch {
            if (isInterest) {
                postInterestReportAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 등록 중 오류가 발생했습니다."
                    }
                )
            } else {
                deleteInterestReportAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 해제 중 오류가 발생했습니다."
                    }
                )
            }
        }
    }

    fun deleteReport(reportId: Long) {
        viewModelScope.launch {
            deleteReportUseCase(reportId).fold(
                onSuccess = {},
                onFailure = {
                    _errorMessage.value = it.message ?: "신고 삭제 중 오류가 발생했습니다."
                }
            )
        }
    }
}