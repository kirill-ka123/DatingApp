package com.example.datingapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.datingapp.domain.useCases.GetUserFromDbUseCase
import com.example.datingapp.domain.useCases.SignInUseCase
import com.example.datingapp.domain.useCases.SignUpUseCase
import com.example.datingapp.domain.useCases.SaveUserInDbUseCase

class UserViewModelFactory(
    val app: Application,
    private val readUserFromDbUseCase: GetUserFromDbUseCase,
    private val writeUserInDbUseCase: SaveUserInDbUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            app,
            readUserFromDbUseCase,
            writeUserInDbUseCase,
            signInUseCase,
            signUpUseCase
        ) as T
    }
}
