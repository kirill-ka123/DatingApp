package com.example.datingapp.domain.models

import java.io.Serializable

data class UserProfile(val userId: String? = null,
                       val name: String? = null,
                       val email: String? = null) : Serializable