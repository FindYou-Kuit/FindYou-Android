package com.example.findu.presentation.ui.my

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findu.domain.model.my.MyProfileData
import com.example.findu.domain.model.my.MyProfileImageUpdate
import com.example.findu.domain.usecase.interest.DeleteInterestAnimalUseCase
import com.example.findu.domain.usecase.interest.PostInterestAnimalUseCase
import com.example.findu.domain.usecase.my.DeleteUserUseCase
import com.example.findu.domain.usecase.my.GetInterestUseCase
import com.example.findu.domain.usecase.my.GetNickNameUseCase
import com.example.findu.domain.usecase.my.GetReportHistoryUseCase
import com.example.findu.domain.usecase.my.GetViewedAnimalUseCase
import com.example.findu.domain.usecase.my.PatchNickNameUseCase
import com.example.findu.domain.usecase.my.PatchProfileImageUseCase
import com.example.findu.domain.usecase.report.DeleteReportUseCase
import com.example.findu.domain.usecase.token.ClearTokenUseCase
import com.example.findu.presentation.mapper.todomain.toRvModel
import com.example.findu.presentation.model.MyInterestRv
import com.example.findu.presentation.model.MyReportHistoryRv
import com.example.findu.presentation.model.MyViewedAnimalsRv
import com.example.findu.presentation.util.UriUtil.toSingleImageFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getInterestUseCase: GetInterestUseCase,
    private val getReportHistoryUseCase: GetReportHistoryUseCase,
    private val getViewedAnimalUseCase: GetViewedAnimalUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val patchNickNameUseCase: PatchNickNameUseCase,
    private val getNickNameUseCase: GetNickNameUseCase,
    private val postInterestAnimalUseCase: PostInterestAnimalUseCase,
    private val deleteInterestAnimalUseCase: DeleteInterestAnimalUseCase,
    private val deleteReportUseCase: DeleteReportUseCase,
    private val patchProfileImageUseCase : PatchProfileImageUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
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

    private val _myProfile = MutableStateFlow<MyProfileData?>(null)
    val myProfile = _myProfile.asStateFlow()


    private val _selectedImageResId = MutableStateFlow<Int?>(null)
    val selectedImageResId = _selectedImageResId.asStateFlow()

    private val _selectedProfileImageUri = MutableStateFlow<Uri?>(null)
    val selectedProfileImageUri: StateFlow<Uri?> = _selectedProfileImageUri

    private val _alarmEnabled = MutableStateFlow(false)
    val alarmEnabled: StateFlow<Boolean> = _alarmEnabled

    fun fetchInterestAnimals() {
        viewModelScope.launch {
            getInterestUseCase(
                lastId = Long.MAX_VALUE,
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
                lastId = Long.MAX_VALUE,
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
                lastId = Long.MAX_VALUE,
            ).fold(
                onSuccess = { data ->
                    _viewedAnimals.value = data.cards.map { it.toRvModel() }
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


    fun updateProfileImage(enumName: String) {
        viewModelScope.launch {
            val update = MyProfileImageUpdate.Default(enumName)

            patchProfileImageUseCase.upload(update).fold(
                onSuccess = { fetchMyProfile() },
                onFailure = { _errorMessage.value = it.message ?: "프로필 이미지 변경 중 오류 발생" }
            )
        }
    }

    fun updateProfileImageFromGallery(context: Context, uri: Uri) {
        viewModelScope.launch {
            var file: java.io.File? = null
            try {
                file = withContext(Dispatchers.IO) {
                    uri.toSingleImageFile(context)
                }
                val update = MyProfileImageUpdate.FilePath(file.absolutePath)

                patchProfileImageUseCase.upload(update).fold(
                    onSuccess = {
                        fetchMyProfile()
                    },
                    onFailure = { e ->
                        _errorMessage.value = e.message ?: "프로필 이미지 변경 중 오류 발생"
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "이미지 파일 변환 중 오류 발생"
            } finally {
                runCatching {
                    if (file?.exists() == true) file.delete()
                }
            }
        }
    }

    fun toggleAlarmSetting() {
        _alarmEnabled.value = !_alarmEnabled.value
    }

    fun updateNickName(newNickName: String) {
        _nickNameState.value = newNickName

        viewModelScope.launch {
            Log.d("MyViewModel", "닉네임 변경 요청 시작: $newNickName")

            patchNickNameUseCase(newNickName).fold(
                onSuccess = {
                    Log.d("MyViewModel", "닉네임 변경 성공")
                    fetchMyProfile()
                },
                onFailure = { e ->
                    Log.e("MyViewModel", "닉네임 변경 실패 : ${e.message}", e)
                    _errorMessage.value = e.message ?: "닉네임 변경 중 오류가 발생했습니다."
                }
            )
        }
    }


    fun fetchMyProfile() {
        viewModelScope.launch {
            getNickNameUseCase().fold(
                onSuccess = { data ->
                    _myProfile.value = data
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "프로필 정보를 불러오는 중 오류가 발생했습니다."
                }
            )
        }
    }

    fun setInterest(id: Long, isInterest: Boolean) {
        viewModelScope.launch {
            val result = if (isInterest) {
                postInterestAnimalUseCase(id).fold(
                    onSuccess = {},
                    onFailure = {
                        _errorMessage.value = it.message ?: "관심 등록 중 오류가 발생했습니다."
                    }
                )
            } else {
                deleteInterestAnimalUseCase(id).fold(
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

    fun clearToken() {
        viewModelScope.launch {
            clearTokenUseCase()
        }
    }

}