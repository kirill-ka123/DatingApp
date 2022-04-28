package com.example.datingapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.datingapp.repository.AuthRepository

class AuthViewModelProviderFactory(
    val app: Application,
    val weatherRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(app, weatherRepository) as T
    }
}
