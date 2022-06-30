package com.example.datingapp.data.database

import com.example.datingapp.data.database.model.UserProfileData
import com.example.datingapp.domain.callbacks.GetUserCallback
import com.example.datingapp.domain.callbacks.SaveUserCallback
import com.example.datingapp.domain.models.UserProfile
import com.google.firebase.database.DatabaseReference
import java.lang.Exception

class FirebaseUserDatabase(private val database: DatabaseReference) : UserDatabase {
    override fun save(userProfileData: UserProfileData, saveUserCallback: SaveUserCallback) {
        database.child("users").child(userProfileData.userId!!)
            .setValue(userProfileData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserCallback.onSuccess()
                } else saveUserCallback.onFailure(task.exception)
            }
    }

    override fun get(userId: String, getUserCallback: GetUserCallback) {
        database.child("users").child(userId).get().addOnSuccessListener {
            val userProfileData = convertAnyToUserProfile(it.value)

            if (userProfileData != null) {
                getUserCallback.onSuccess(userProfileData)
            } else getUserCallback.onFailure(Exception("value is null"))
        }.addOnFailureListener {
            getUserCallback.onFailure(it)
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