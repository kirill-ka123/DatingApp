package com.example.datingapp.domain.callbacks

import java.lang.Exception

interface SignUpCallback {
    fun onStartSignUp()
    fun onSuccessSignUp()
    fun onFailureSignUp(error: Exception?)
}