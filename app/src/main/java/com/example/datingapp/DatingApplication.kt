package com.example.datingapp

import android.app.Application
import com.example.datingapp.data.repositories.UserRepositoryImpl
import com.example.datingapp.domain.useCases.GetUserFromDbUseCase
import com.example.datingapp.domain.useCases.SignInUseCase
import com.example.datingapp.domain.useCases.SignUpUseCase
import com.example.datingapp.domain.useCases.SaveUserInDbUseCase
import com.example.datingapp.presentation.di.ServiceLocator

class DatingApplication : Application() {
    private val userRepository: UserRepositoryImpl
        get() = ServiceLocator.provideUserRepository()

    val readUserFromDbUseCase: GetUserFromDbUseCase
        get() = GetUserFromDbUseCase(userRepository)

    val writeUserInDbUseCase: SaveUserInDbUseCase
        get() = SaveUserInDbUseCase(userRepository)

    val signInUseCase: SignInUseCase
        get() = SignInUseCase(userRepository)

    val signUpUseCase: SignUpUseCase
        get() = SignUpUseCase(userRepository)
}