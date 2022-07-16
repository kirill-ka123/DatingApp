package com.example.datingapp.data.auth

import com.example.datingapp.data.auth.model.UserEmailAndPasswordData
import com.example.datingapp.domain.callbacks.SignInCallback
import com.example.datingapp.domain.callbacks.SignUpCallback

interface UserAuth {
    fun signIn(
        userEmailAndPasswordData: UserEmailAndPasswordData,
        signInCallback: SignInCallback
    )

    fun signUp(
        userEmailAndPasswordData: UserEmailAndPasswordData,
        signUpCallback: SignUpCallback
    )
}