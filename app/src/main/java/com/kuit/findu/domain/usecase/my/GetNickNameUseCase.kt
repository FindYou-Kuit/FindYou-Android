package com.kuit.findu.domain.usecase.my

import com.kuit.findu.domain.repository.MyRepository
import javax.inject.Inject

class GetNickNameUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend operator fun invoke() = myRepository.getNickname()
}