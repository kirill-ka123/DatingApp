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
        signInCallback.onStartSignIn()
        auth.signInWithEmailAndPassword(
            userEmailAndPasswordData.email,
            userEmailAndPasswordData.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                signInCallback.onSuccessSignIn()
            } else signInCallback.onFailureSignIn(task.exception)
        }
    }

    override fun signUp(
        userEmailAndPasswordData: UserEmailAndPasswordData,
        signUpCallback: SignUpCallback
    ) {
        signUpCallback.onStartSignUp()
        auth.createUserWithEmailAndPassword(
            userEmailAndPasswordData.email,
            userEmailAndPasswordData.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                signUpCallback.onSuccessSignUp()
            } else signUpCallback.onFailureSignUp(task.exception)
        }
    }
}