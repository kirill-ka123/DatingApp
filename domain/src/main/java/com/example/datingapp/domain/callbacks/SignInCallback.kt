package com.example.datingapp.domain.callbacks

import java.lang.Exception

interface SignInCallback {
    fun onStartSignIn()
    fun onSuccessSignIn()
    fun onFailureSignIn(error: Exception?)
}