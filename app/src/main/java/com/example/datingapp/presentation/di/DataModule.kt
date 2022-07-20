package com.example.datingapp.presentation.di

import com.example.datingapp.data.auth.FirebaseUserAuth
import com.example.datingapp.data.auth.UserAuth
import com.example.datingapp.data.database.FirebaseUserDatabase
import com.example.datingapp.data.database.UserDatabase
import com.example.datingapp.data.repositories.UserRepositoryImpl
import com.example.datingapp.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val dataModule = module {
    single<UserAuth> {
        val auth = FirebaseAuth.getInstance().apply {
            setLanguageCode("ru")
        }
        FirebaseUserAuth(auth)
    }

    single<UserDatabase> {
        val database = Firebase.database.reference
        FirebaseUserDatabase(database)
    }

    single<UserRepository> {
        UserRepositoryImpl(userAuth = get(), userDatabase = get())
    }
}