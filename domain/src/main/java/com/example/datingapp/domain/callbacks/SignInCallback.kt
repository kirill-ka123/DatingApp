package com.example.datingapp.domain.callbacks

import java.lang.Exception

interface SignInCallback {
    fun onSuccess()
    fun onFailure(error: Exception?)
}