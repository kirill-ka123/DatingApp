package com.example.datingapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datingapp.repository.AuthRepository
import com.example.datingapp.util.ResourceAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(
    app: Application,
    private val authRepository: AuthRepository
) : AndroidViewModel(app) {
    private val _user = MutableLiveData<ResourceAuth<FirebaseUser>>()
    val user: LiveData<ResourceAuth<FirebaseUser>> = _user
    private val mAuth = FirebaseAuth.getInstance()

    init {
        setUser(mAuth.currentUser)
    }

    fun setUser(user: FirebaseUser?) {
        if (user != null) {
            _user.value = ResourceAuth.Success(user)
        } else {
            _user.value = ResourceAuth.Null()
        }
    }

    fun signIn(email: String, password: String) {
        _user.value = ResourceAuth.Loading()
        authRepository.signIn(email, password, this)
    }

    fun onSignInResult(result: ResourceAuth<FirebaseUser>) {
        _user.value = result
    }
}