package com.example.datingapp.domain.callbacks

import com.example.datingapp.domain.models.UserProfile
import java.lang.Exception

interface GetUserCallback {
    fun onStartGetUser()
    fun onSuccessGetUser(userProfile: UserProfile)
    fun onFailureGetUser(error: Exception)
}