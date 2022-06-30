package com.example.datingapp.domain.callbacks

import java.lang.Exception

interface SaveUserCallback {
    fun onSuccess()
    fun onFailure(error: Exception?)
}