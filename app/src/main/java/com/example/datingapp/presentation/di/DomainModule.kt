package com.example.datingapp.presentation.di

import com.example.datingapp.domain.useCases.*
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
    factory {
        ResetPasswordUseCase(userRepository = get())
    }
}