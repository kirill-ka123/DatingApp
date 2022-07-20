package com.example.datingapp.domain.callbacks

import java.lang.Exception

interface ResetPasswordCallback {
    fun onStartResetPassword()
    fun onSuccessResetPassword()
    fun onFailureResetPassword(error: Exception)
}