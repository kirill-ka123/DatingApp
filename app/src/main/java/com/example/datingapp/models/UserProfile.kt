package com.example.datingapp.models

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class UserProfile(val userId: String? = null, val name: String? = null, val email: String? = null) : Serializable