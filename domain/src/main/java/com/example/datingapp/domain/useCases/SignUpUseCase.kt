package com.example.datingapp.domain.useCases

import com.example.datingapp.domain.callbacks.SignUpCallback
import com.example.datingapp.domain.models.UserEmailAndPassword
import com.example.datingapp.domain.repositories.UserRepository

class SignUpUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        userEmailAndPassword: UserEmailAndPassword,
        signUpCallback: SignUpCallback
    ) {
        userRepository.signUp(userEmailAndPassword, signUpCallback)
    }
}