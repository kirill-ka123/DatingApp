package com.example.datingapp.data.auth

import com.example.datingapp.data.auth.model.UserEmailAndPasswordData
import com.example.datingapp.domain.callbacks.ResetPasswordCallback
import com.example.datingapp.domain.callbacks.SignInCallback
import com.example.datingapp.domain.callbacks.SignUpCallback
import com.google.firebase.auth.ActionCodeSettings
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
        ).addOnSuccessListener {
            signInCallback.onSuccessSignIn()
        }.addOnFailureListener { exception ->
            signInCallback.onFailureSignIn(exception)
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
        ).addOnSuccessListener {
            signUpCallback.onSuccessSignUp()
        }.addOnFailureListener { exception ->
            signUpCallback.onFailureSignUp(exception)
        }
    }

    override fun resetPassword(email: String, resetPasswordCallback: ResetPasswordCallback) {

        val url =
            "https://datingapp1.page.link"
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl(url)
            .setAndroidPackageName("com.example.datingapp", false, null)
            .build()

        resetPasswordCallback.onStartResetPassword()
        auth.sendPasswordResetEmail(email, actionCodeSettings)
            .addOnSuccessListener {
                resetPasswordCallback.onSuccessResetPassword()
            }.addOnFailureListener { exception ->
                resetPasswordCallback.onFailureResetPassword(exception)
            }
    }
}