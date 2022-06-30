package com.example.datingapp.domain.useCases

import com.example.datingapp.domain.callbacks.GetUserCallback
import com.example.datingapp.domain.repositories.UserRepository

class GetUserFromDbUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userId: String, getUserCallback: GetUserCallback) =
        userRepository.getUserFormDatabase(userId, getUserCallback)
}