package com.example.findu.domain.usecase.my

import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class PatchNickNameUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend operator fun invoke(nickName: String) = myRepository.patchNickname(nickName)
}