package com.example.datingapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.datingapp.repository.UserRepository

class AuthViewModelProviderFactory(
    val app: Application,
    val weatherRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(app, weatherRepository) as T
    }
}
