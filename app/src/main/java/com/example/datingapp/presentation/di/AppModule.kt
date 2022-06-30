package com.example.datingapp.presentation.di

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.datingapp.presentation.viewModel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        UserViewModel(
            app = get(),
            getUserFromDbUseCase = get(),
            saveUserInDbUseCase = get(),
            signInUseCase = get(),
            signUpUseCase = get()
        )
    }
}