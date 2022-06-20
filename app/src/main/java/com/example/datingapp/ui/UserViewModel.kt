package com.example.datingapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datingapp.models.UserProfile
import com.example.datingapp.repository.UserRepository
import com.example.datingapp.util.ResourceAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserViewModel(
    app: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(app) {

    private var user: FirebaseUser? = null
    private val _userProfile = MutableLiveData<ResourceAuth<UserProfile>>()
    val userProfile: LiveData<ResourceAuth<UserProfile>> =
        _userProfile // Полный профиль текущего пользователя, который сохраняется в БД

    private val auth = FirebaseAuth.getInstance()

    init {
        setUser()
    }

    fun setUser() {
        if (user == null && auth.currentUser != null) {
            user = auth.currentUser

            _userProfile.value = ResourceAuth.Loading()
            userRepository.readUserFormDatabase(user!!.uid)
                .addOnSuccessListener { // получение профиля пользователя из БД
                    Log.i("firebase", "Got value ${it.value}")

                    val userProfileData = convertAnyToUserProfileData(it.value)
                    if (userProfileData != null) {
                        _userProfile.value = ResourceAuth.Success(userProfileData)
                    } else _userProfile.value = ResourceAuth.Null()
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
        }
    }

    fun setUser(user: FirebaseUser?) {
        this.user = user
    }

    fun setUserProfile(userProfile: ResourceAuth<UserProfile>) {
        _userProfile.value = userProfile
    }

    fun signIn(email: String, password: String) {
        _userProfile.value = ResourceAuth.Loading()

        userRepository.signIn(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("authentication", "signInWithEmail:success")
                if (auth.currentUser != null) {
                    user = auth.currentUser!!

                    // получение профиля пользователя из БД
                    userRepository.readUserFormDatabase(auth.currentUser!!.uid).addOnSuccessListener {
                        Log.i("firebase", "Got value ${it.value}")

                        val userProfileData = convertAnyToUserProfileData(it.value)
                        val userProfile = ResourceAuth.Success(userProfileData)
                        if (userProfileData != null) {
                            _userProfile.value = ResourceAuth.Success(userProfileData)
                        } else _userProfile.value = ResourceAuth.Null()
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                } else {
                    val userProfile = ResourceAuth.Error<UserProfile>("user is nullable")
                    _userProfile.value = userProfile
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w("authentication", "signInWithEmail:failure", task.exception)
                val userProfile = ResourceAuth.Error<UserProfile>(task.exception?.message)
                _userProfile.value = userProfile
            }
        }
    }

    fun signUp(email: String, password: String, name: String) {
        _userProfile.value = ResourceAuth.Loading()
        userRepository.signUp(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("authentication", "createUserWithEmail:success")
                if (auth.currentUser != null) {
                    user = auth.currentUser!!

                    val userProfileData = UserProfile(auth.currentUser!!.uid, name, email)
                    val userProfile = ResourceAuth.Success(userProfileData)

                    // записываем профиль нового пользователя в БД
                    userRepository.writeNewUserInDatabase(userProfileData)
                    _userProfile.value = userProfile
                } else {
                    val userProfile = ResourceAuth.Error<UserProfile>("user is nullable")
                    _userProfile.value = userProfile
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w("authentication", "createUserWithEmail:failure", task.exception)
                val userProfile = ResourceAuth.Error<UserProfile>(task.exception?.message)
                _userProfile.value = userProfile
            }
        }
    }

    private fun convertAnyToUserProfileData(value: Any?): UserProfile? {
        return if (value != null) {
            val userId = (value as HashMap<*, *>)["userId"] as String?
            val name = (value as HashMap<*, *>)["name"] as String?
            val email = (value as HashMap<*, *>)["email"] as String?

            UserProfile(userId, name, email)
        } else null
    }
}