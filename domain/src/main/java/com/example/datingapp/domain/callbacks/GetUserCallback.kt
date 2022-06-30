package com.example.datingapp.domain.callbacks

import com.example.datingapp.domain.models.UserProfile
import java.lang.Exception

interface GetUserCallback {
    fun onSuccess(userProfile: UserProfile)
    fun onFailure(error: Exception)
}