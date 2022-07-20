package com.example.datingapp.domain.useCases

import com.example.datingapp.domain.callbacks.ResetPasswordCallback
import com.example.datingapp.domain.repositories.UserRepository

class ResetPasswordUseCase(private val userRepository: UserRepository) {
    operator fun invoke(email: String, resetPasswordCallback: ResetPasswordCallback) =
        userRepository.resetPassword(email, resetPasswordCallback)
}