package com.example.datingapp.data.database

import com.example.datingapp.data.database.model.UserProfileData
import com.example.datingapp.domain.callbacks.GetUserCallback
import com.example.datingapp.domain.callbacks.SaveUserCallback
import com.example.datingapp.domain.models.UserProfile
import com.google.firebase.database.DatabaseReference
import java.lang.Exception

class FirebaseUserDatabase(private val database: DatabaseReference) : UserDatabase {
    override fun save(userProfileData: UserProfileData, saveUserCallback: SaveUserCallback) {
        saveUserCallback.onStartSaveUser()
        database.child("users").child(userProfileData.userId!!)
            .setValue(userProfileData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserCallback.onSuccessSaveUser()
                } else saveUserCallback.onFailureSaveUser(task.exception)
            }
    }

    override fun get(userId: String, getUserCallback: GetUserCallback) {
        getUserCallback.onStartGetUser()
        database.child("users").child(userId).get().addOnSuccessListener {
            val userProfileData = convertAnyToUserProfile(it.value)

            if (userProfileData != null) {
                getUserCallback.onSuccessGetUser(userProfileData)
            } else getUserCallback.onFailureGetUser(Exception("value is null"))
        }.addOnFailureListener {
            getUserCallback.onFailureGetUser(it)
        }
    }


    private fun convertAnyToUserProfile(value: Any?): UserProfile? {
        return if (value != null) {
            val userId = (value as HashMap<*, *>)["userId"] as String?
            val name = (value as HashMap<*, *>)["name"] as String?
            val email = (value as HashMap<*, *>)["email"] as String?

            UserProfile(userId, name, email)
        } else null
    }
}