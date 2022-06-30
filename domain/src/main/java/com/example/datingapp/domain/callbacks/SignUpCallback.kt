package com.example.datingapp.domain.callbacks

import java.lang.Exception

interface SignUpCallback {
    fun onSuccess()
    fun onFailure(error: Exception?)
}