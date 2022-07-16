package com.example.datingapp.domain.callbacks

import java.lang.Exception

interface SaveUserCallback {
    fun onStartSaveUser()
    fun onSuccessSaveUser()
    fun onFailureSaveUser(error: Exception?)
}