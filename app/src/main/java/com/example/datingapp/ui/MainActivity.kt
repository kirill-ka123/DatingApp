package com.example.datingapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.datingapp.R
import com.example.datingapp.repository.AuthRepository

class MainActivity : AppCompatActivity() {
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authRepository = AuthRepository()
        val viewModelProviderFactory = AuthViewModelProviderFactory(application, authRepository)
        authViewModel = ViewModelProvider(this, viewModelProviderFactory)[AuthViewModel::class.java]

        setContentView(R.layout.activity_main)
    }
}