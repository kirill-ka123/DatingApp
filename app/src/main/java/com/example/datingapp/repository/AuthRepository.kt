package com.example.datingapp.repository

import android.util.Log
import com.example.datingapp.ui.AuthViewModel
import com.example.datingapp.util.Constant
import com.example.datingapp.util.ResourceAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {
    private val mAuth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String, authViewModel: AuthViewModel) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(Constant.TAG_LOG, "signInWithEmail:success")
                    if (mAuth.currentUser != null) {
                        val user = ResourceAuth.Success(mAuth.currentUser!!)
                        authViewModel.onSignInResult(user)
                    } else {
                        val user = ResourceAuth.Error<FirebaseUser>("user is nullable")
                        authViewModel.onSignInResult(user)
                    }
                } else {
                    Log.w(Constant.TAG_LOG, "signInWithEmail:failure", task.exception)
                    val user = ResourceAuth.Error<FirebaseUser>(task.exception?.message)
                    authViewModel.onSignInResult(user)
                }
            }
    }

    fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constant.TAG_LOG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constant.TAG_LOG, "createUserWithEmail:failure", task.exception)
                    //updateUI(null)
                }
            }
    }
}