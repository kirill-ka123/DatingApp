package com.example.datingapp.presentation.di

import com.example.datingapp.data.auth.FirebaseUserAuth
import com.example.datingapp.data.database.FirebaseUserDatabase
import com.example.datingapp.data.repositories.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object ServiceLocator {
    private var firebaseUserDatabase: FirebaseUserDatabase? = null
    private var firebaseUserAuth: FirebaseUserAuth? = null

    @Volatile
    var userRepository: UserRepositoryImpl? = null

    fun provideUserRepository(): UserRepositoryImpl {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return userRepository ?: createUserRepository()
        }
    }

    private fun createUserRepository(): UserRepositoryImpl {

        val firebaseUserAuth = firebaseUserAuth ?: createFirebaseUserAuth()
        val firebaseUserDatabase = firebaseUserDatabase ?: createFirebaseUserDatabase()
        val newRepo = UserRepositoryImpl(firebaseUserAuth, firebaseUserDatabase)
        userRepository = newRepo
        return newRepo
    }

    private fun createFirebaseUserAuth(): FirebaseUserAuth {
        val auth = FirebaseAuth.getInstance()
        firebaseUserAuth = FirebaseUserAuth(auth)
        return firebaseUserAuth as FirebaseUserAuth
    }

    private fun createFirebaseUserDatabase(): FirebaseUserDatabase {
        val database = Firebase.database.reference
        firebaseUserDatabase = FirebaseUserDatabase(database)
        return firebaseUserDatabase as FirebaseUserDatabase
    }
}