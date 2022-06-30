package com.example.datingapp.data.database

import com.example.datingapp.data.database.model.UserProfileData
import com.example.datingapp.domain.callbacks.GetUserCallback
import com.example.datingapp.domain.callbacks.SaveUserCallback

interface UserDatabase {
    fun save(userProfileData: UserProfileData, saveUserCallback: SaveUserCallback)

    fun get(userId: String, getUserCallback: GetUserCallback)
}