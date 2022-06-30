package com.example.datingapp.data.database.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class UserProfileData(val userId: String? = null,
                           val name: String? = null,
                           val email: String? = null) : Serializable