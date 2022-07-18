package com.example.datingapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.databinding.RegisterFragmentBinding
import com.example.datingapp.presentation.MainActivity
import com.example.datingapp.presentation.viewModel.UserViewModel
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : Fragment(R.layout.register_fragment) {
    private lateinit var binding: RegisterFragmentBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = RegisterFragmentBinding.inflate(layoutInflater)
        userViewModel = (activity as MainActivity).userViewModel
        navController = findNavController()
        setupListeners()

        btn_sign_up.setOnClickListener {
            val name = et_name.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()

            if (isValidate()) {
                userViewModel.signUp(email, password, name)
            }
        }

        userViewModel.loginUi.observe(viewLifecycleOwner) { loginUi ->
            loginUi.apply(pb_sign_up, navController, this)
        }
    }

    private fun isValidate(): Boolean =
        userViewModel.validateName(
            et_name,
            lt_name
        ) && userViewModel.validateEmail(
            et_email,
            lt_email
        ) && userViewModel.validatePassword(
            et_password,
            lt_password
        ) && userViewModel.validateConfirmPassword(
            et_confirm_password,
            lt_confirm_password,
            et_password
        )

    private fun setupListeners() {
        et_name.addTextChangedListener(TextFieldValidation(et_name))
        et_email.addTextChangedListener(TextFieldValidation(et_email))
        et_password.addTextChangedListener(TextFieldValidation(et_password))
        et_confirm_password.addTextChangedListener(TextFieldValidation(et_confirm_password))
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.et_name -> {
                    userViewModel.validateName(et_name, lt_name)
                }
                R.id.et_email -> {
                    userViewModel.validateEmail(et_email, lt_email)
                }
                R.id.et_password -> {
                    userViewModel.validatePassword(et_password, lt_password)
                }
                R.id.et_confirm_password -> {
                    userViewModel.validateConfirmPassword(
                        et_confirm_password,
                        lt_confirm_password,
                        et_password
                    )
                }
            }
        }
    }
}