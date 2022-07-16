package com.example.datingapp.presentation.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.presentation.MainActivity
import com.example.datingapp.presentation.viewModel.UserViewModel
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private lateinit var userViewModel: UserViewModel
    
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = (activity as MainActivity).userViewModel
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

        userViewModel.loginUi.observe(viewLifecycleOwner) { loginUi ->
            loginUi.apply(pb_sign_in, navController, requireContext(), this)
        }
    }

    override fun onStart() {
        super.onStart()
        userViewModel.setUser()
    }
}