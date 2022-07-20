package com.example.datingapp.domain.repositories

import com.example.datingapp.domain.callbacks.*
import com.example.datingapp.domain.models.UserEmailAndPassword
import com.example.datingapp.domain.models.UserProfile

interface UserRepository {
    fun saveNewUserInDatabase(userProfile: UserProfile, saveUserCallback: SaveUserCallback)

    fun getUserFormDatabase(userId: String, getUserCallback: GetUserCallback)

    fun signIn(userEmailAndPassword: UserEmailAndPassword, signInCallback: SignInCallback)

    fun signUp(userEmailAndPassword: UserEmailAndPassword, signUpCallback: SignUpCallback)

    fun resetPassword(email: String, resetPasswordCallback: ResetPasswordCallback)
}