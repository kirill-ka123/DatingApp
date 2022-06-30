package com.example.datingapp.presentation.di

import com.example.datingapp.domain.useCases.GetUserFromDbUseCase
import com.example.datingapp.domain.useCases.SaveUserInDbUseCase
import com.example.datingapp.domain.useCases.SignInUseCase
import com.example.datingapp.domain.useCases.SignUpUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetUserFromDbUseCase(userRepository = get())
    }
    factory {
        SaveUserInDbUseCase(userRepository = get())
    }
    factory {
        SignInUseCase(userRepository = get())
    }
    factory {
        SignUpUseCase(userRepository = get())
    }
}