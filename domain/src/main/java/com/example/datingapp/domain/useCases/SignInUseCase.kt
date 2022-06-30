package com.example.datingapp.domain.useCases

import com.example.datingapp.domain.callbacks.SignInCallback
import com.example.datingapp.domain.repositories.UserRepository
import com.example.datingapp.domain.models.UserEmailAndPassword

class SignInUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        userEmailAndPassword: UserEmailAndPassword,
        signInCallback: SignInCallback
    ) = userRepository.signIn(userEmailAndPassword, signInCallback)
}