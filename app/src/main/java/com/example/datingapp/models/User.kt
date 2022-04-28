package com.example.datingapp.models

import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class User(val displayName: String, val email: String) : Serializable {
    constructor(
        firebaseUser: FirebaseUser,
        displayName: String = firebaseUser.displayName.toString(),
        email: String = firebaseUser.email.toString()
    ) : this(displayName, email)
}