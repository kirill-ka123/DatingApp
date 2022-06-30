package com.example.datingapp.presentation.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datingapp.domain.models.UserProfile
import com.example.datingapp.common.ResourceAuth
import com.example.datingapp.domain.callbacks.GetUserCallback
import com.example.datingapp.domain.callbacks.SaveUserCallback
import com.example.datingapp.domain.callbacks.SignInCallback
import com.example.datingapp.domain.callbacks.SignUpCallback
import com.example.datingapp.domain.models.UserEmailAndPassword
import com.example.datingapp.domain.useCases.GetUserFromDbUseCase
import com.example.datingapp.domain.useCases.SignInUseCase
import com.example.datingapp.domain.useCases.SignUpUseCase
import com.example.datingapp.domain.useCases.SaveUserInDbUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class UserViewModel(
    app: Application,
    private val getUserFromDbUseCase: GetUserFromDbUseCase,
    private val saveUserInDbUseCase: SaveUserInDbUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
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
            getUserFromDbUseCase(user!!.uid, object : GetUserCallback {
                override fun onSuccess(userProfile: UserProfile) {
                    Log.i("firebase database", "Got value $userProfile")

                    _userProfile.value = ResourceAuth.Success(userProfile)
                }

                override fun onFailure(error: Exception) {
                    Log.e("firebase database", "Error getting data", error)

                    val userProfile = ResourceAuth.Error<UserProfile>(error.message)
                    _userProfile.value = userProfile
                }
            })
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

        val userEmailAndPassword = UserEmailAndPassword(email, password)
        signInUseCase(userEmailAndPassword, object : SignInCallback {
            override fun onSuccess() {
                Log.d("authentication", "signInWithEmail:success")

                if (auth.currentUser != null) {
                    user = auth.currentUser!!

                    // получение профиля пользователя из БД
                    getUserFromDbUseCase(auth.currentUser!!.uid, object : GetUserCallback {
                        override fun onSuccess(userProfile: UserProfile) {
                            Log.i("firebase database", "Got value $userProfile")

                            _userProfile.value = ResourceAuth.Success(userProfile)
                        }

                        override fun onFailure(error: Exception) {
                            Log.e("firebase database", "Error getting data", error)

                            val userProfile = ResourceAuth.Error<UserProfile>(error.message)
                            _userProfile.value = userProfile
                        }

                    })
                } else {
                    val userProfile = ResourceAuth.Error<UserProfile>("user is nullable")
                    _userProfile.value = userProfile
                }
            }

            override fun onFailure(error: Exception?) {
                Log.w("authentication", "signInWithEmail:failure", error)
                val userProfile = ResourceAuth.Error<UserProfile>(error?.message)
                _userProfile.value = userProfile
            }
        })
    }

    fun signUp(email: String, password: String, name: String) {
        _userProfile.value = ResourceAuth.Loading()

        val userEmailAndPassword = UserEmailAndPassword(email, password)
        signUpUseCase(userEmailAndPassword, object : SignUpCallback {
            override fun onSuccess() {
                Log.d("authentication", "createUserWithEmail:success")
                if (auth.currentUser != null) {
                    user = auth.currentUser!!

                    val userProfileValue = UserProfile(auth.currentUser!!.uid, name, email)
                    val userProfile = ResourceAuth.Success(userProfileValue)

                    // записываем профиль нового пользователя в БД
                    saveUserInDbUseCase(userProfileValue, object : SaveUserCallback {
                        override fun onSuccess() {
                            Log.w("database", "saveUserInDatabase:success")
                            _userProfile.value = userProfile
                        }

                        override fun onFailure(error: Exception?) {
                            Log.w("database", "saveUserInDatabase:failure", error)
                            val userProfileError = ResourceAuth.Error<UserProfile>(error?.message)
                            _userProfile.value = userProfileError
                        }

                    })
                } else {
                    val userProfile = ResourceAuth.Error<UserProfile>("user is nullable")
                    _userProfile.value = userProfile
                }
            }

            override fun onFailure(error: Exception?) {
                Log.w("authentication", "createUserWithEmail:failure", error)
                val userProfile = ResourceAuth.Error<UserProfile>(error?.message)
                _userProfile.value = userProfile
            }

        })
    }
}