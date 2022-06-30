package com.example.datingapp.data.auth

import com.example.datingapp.data.auth.model.UserEmailAndPasswordData
import com.example.datingapp.domain.callbacks.SignInCallback
import com.example.datingapp.domain.callbacks.SignUpCallback
import com.google.firebase.auth.FirebaseAuth

class FirebaseUserAuth(private val auth: FirebaseAuth) : UserAuth {
    override fun signIn(
        userEmailAndPasswordData: UserEmailAndPasswordData,
        signInCallback: SignInCallback
    ) {
        auth.signInWithEmailAndPassword(
            userEmailAndPasswordData.email,
            userEmailAndPasswordData.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                signInCallback.onSuccess()
            } else signInCallback.onFailure(task.exception)
        }
    }

    override fun signUp(
        userEmailAndPasswordData: UserEmailAndPasswordData,
        signUpCallback: SignUpCallback
    ) {
        auth.createUserWithEmailAndPassword(
            userEmailAndPasswordData.email,
            userEmailAndPasswordData.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                signUpCallback.onSuccess()
            } else signUpCallback.onFailure(task.exception)
        }
    }
}