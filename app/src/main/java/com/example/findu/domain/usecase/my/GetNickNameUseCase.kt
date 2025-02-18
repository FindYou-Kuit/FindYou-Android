package com.example.findu.domain.usecase.my

import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class GetNickNameUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend operator fun invoke() = myRepository.getNickname()
}