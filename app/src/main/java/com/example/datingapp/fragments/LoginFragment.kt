package com.example.datingapp.fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.models.UserProfile
import com.example.datingapp.ui.MainActivity
import com.example.datingapp.ui.UserViewModel
import com.example.datingapp.util.Constant.Companion.TAG_LOG
import com.example.datingapp.util.ResourceAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private lateinit var userViewModel: UserViewModel
    private lateinit var database: DatabaseReference
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = (activity as MainActivity).userViewModel
        database = Firebase.database.reference
        navController = findNavController()

        btn_sign_in.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotBlank()) {
                userViewModel.signIn(email, password)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Некорректный email", Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(requireContext(), "Некорректный пароль", Toast.LENGTH_SHORT).show()
            }
        }

        btn_sign_up.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        userViewModel.userProfile.observe(viewLifecycleOwner, Observer { user ->
            when (user) {
                is ResourceAuth.Success -> {
                    pb_sign_in.visibility = View.INVISIBLE

                    val bundle = Bundle().apply {
//                        if (user.data != null) {
//                            putSerializable("user", user.data)
//                        } else {
//                            putSerializable("user", UserProfile())
//                        }
                        putSerializable("user", user.data)
                    }
                    navController.navigate(R.id.action_loginFragment_to_mapFragment, bundle)
                }
                is ResourceAuth.Error -> {
                    pb_sign_in.visibility = View.INVISIBLE

                    Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                }
                is ResourceAuth.Loading -> {
                    pb_sign_in.visibility = View.VISIBLE
                }
                is ResourceAuth.Null -> {
                    pb_sign_in.visibility = View.INVISIBLE

                    Toast.makeText(requireContext(), "user is nullable", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        userViewModel.setUser()
    }
}