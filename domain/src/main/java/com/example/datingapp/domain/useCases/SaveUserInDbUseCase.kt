package com.example.datingapp.domain.useCases

import com.example.datingapp.domain.callbacks.SaveUserCallback
import com.example.datingapp.domain.models.UserProfile
import com.example.datingapp.domain.repositories.UserRepository

class SaveUserInDbUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userProfile: UserProfile, saveUserCallback: SaveUserCallback) =
        userRepository.saveNewUserInDatabase(userProfile, saveUserCallback)
}