package com.example.datingapp.repository

import android.util.Log
import com.example.datingapp.models.UserProfile
import com.example.datingapp.ui.UserViewModel
import com.example.datingapp.util.ResourceAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class UserRepository(val auth: FirebaseAuth, val database: DatabaseReference) {

    fun writeNewUserInDatabase(userProfile: UserProfile) {
        database.child("users").child(userProfile.userId!!)
            .setValue(userProfile) // Проверка currentUser на null делается в методе UserViewModel.signUp и сама
        // переменная auth.currentUser!!.uid - NonNull, поэтому допустимо использовать !!
    }

    fun readUserFormDatabase(userId: String) = database.child("users").child(userId).get()

    fun signIn(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)


    fun signUp(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)
}