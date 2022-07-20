package com.example.datingapp.data.repositories

import com.example.datingapp.data.auth.UserAuth
import com.example.datingapp.data.database.UserDatabase
import com.example.datingapp.data.auth.model.UserEmailAndPasswordData
import com.example.datingapp.data.database.model.UserProfileData
import com.example.datingapp.domain.callbacks.*
import com.example.datingapp.domain.models.UserEmailAndPassword
import com.example.datingapp.domain.models.UserProfile
import com.example.datingapp.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userAuth: UserAuth,
    private val userDatabase: UserDatabase
) : UserRepository {
    override fun saveNewUserInDatabase(userProfile: UserProfile, saveUserCallback: SaveUserCallback) {
        val userProfileData = mapToDatabase(userProfile)

        userDatabase.save(userProfileData, saveUserCallback)
    }

    override fun getUserFormDatabase(userId: String, getUserCallback: GetUserCallback) =
        userDatabase.get(userId, getUserCallback)

    override fun signIn(userEmailAndPassword: UserEmailAndPassword, signInCallback: SignInCallback){
        val userEmailAndPasswordData = mapToDatabase(userEmailAndPassword)

       userAuth.signIn(userEmailAndPasswordData, signInCallback)
    }

    override fun signUp(userEmailAndPassword: UserEmailAndPassword, signUpCallback: SignUpCallback) {
        val userEmailAndPasswordData = mapToDatabase(userEmailAndPassword)

        userAuth.signUp(userEmailAndPasswordData, signUpCallback)
    }

    override fun resetPassword(email: String, resetPasswordCallback: ResetPasswordCallback) {
        userAuth.resetPassword(email, resetPasswordCallback)
    }

    private fun mapToDatabase(userEmailAndPassword: UserEmailAndPassword) =
        UserEmailAndPasswordData(userEmailAndPassword.email, userEmailAndPassword.password)

    private fun mapToDatabase(userProfile: UserProfile) =
        UserProfileData(userProfile.userId, userProfile.name, userProfile.email)
}