package com.example.datingapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.datingapp.R
import com.example.datingapp.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Firebase.database.reference
        val auth = FirebaseAuth.getInstance()
        val userRepository = UserRepository(auth, database)
        val viewModelProviderFactory = AuthViewModelProviderFactory(application, userRepository)
        userViewModel = ViewModelProvider(this, viewModelProviderFactory)[UserViewModel::class.java]

        setContentView(R.layout.activity_main)
    }
}