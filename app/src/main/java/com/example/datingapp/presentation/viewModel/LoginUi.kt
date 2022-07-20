package com.example.datingapp.presentation.viewModel

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.datingapp.R
import com.example.datingapp.domain.models.UserProfile
import com.example.datingapp.presentation.fragments.LoginFragment
import com.example.datingapp.presentation.fragments.RegisterFragment

interface LoginUi {
    fun apply(
        progressBar: View,
        navController: NavController,
        fragment: Fragment
    )

    class LoadingLoginUi : LoginUi {
        override fun apply(
            progressBar: View,
            navController: NavController,
            fragment: Fragment
        ) {
            progressBar.visibility = View.VISIBLE
        }
    }

    class SuccessLoginUi(private val userProfile: UserProfile) : LoginUi {
        override fun apply(
            progressBar: View,
            navController: NavController,
            fragment: Fragment
        ) {
            progressBar.visibility = View.INVISIBLE
            val bundle = Bundle().apply {
                putSerializable("user", userProfile)
            }
            if (fragment is LoginFragment) {
                navController.navigate(R.id.action_loginFragment_to_mapFragment, bundle)
            } else if (fragment is RegisterFragment) {
                navController.navigate(R.id.action_registerFragment_to_mapFragment, bundle)
            }
        }
    }

    class ErrorLoginUi(private val message: String?) : LoginUi {
        override fun apply(
            progressBar: View,
            navController: NavController,
            fragment: Fragment
        ) {
            progressBar.visibility = View.INVISIBLE
        }
    }

    class NullLoginUi : LoginUi {
        override fun apply(
            progressBar: View,
            navController: NavController,
            fragment: Fragment
        ) {
            progressBar.visibility = View.INVISIBLE
        }
    }
}