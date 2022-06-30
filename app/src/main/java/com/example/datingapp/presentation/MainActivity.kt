package com.example.datingapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.datingapp.DatingApplication
import com.example.datingapp.R
import com.example.datingapp.presentation.viewModel.UserViewModelFactory
import com.example.datingapp.presentation.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelProviderFactory = UserViewModelFactory(
            application,
            (application as DatingApplication).readUserFromDbUseCase,
            (application as DatingApplication).writeUserInDbUseCase,
            (application as DatingApplication).signInUseCase,
            (application as DatingApplication).signUpUseCase
        )
        userViewModel = ViewModelProvider(this, viewModelProviderFactory)[UserViewModel::class.java]

        setContentView(R.layout.activity_main)
    }
}