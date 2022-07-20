package com.example.datingapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.presentation.MainActivity
import com.example.datingapp.presentation.viewModel.UserViewModel
import kotlinx.android.synthetic.main.reset_password.*

class ResetPasswordFragment : Fragment(R.layout.reset_password) {
    private lateinit var userViewModel: UserViewModel
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = (activity as MainActivity).userViewModel
        navController = findNavController()
        setupListeners()

        btn_reset_password.setOnClickListener {
            val email = et_email.text.toString()

            if (isValidate()) {
                userViewModel.resetPassword(email)
            }
        }

        userViewModel.resetPasswordUi.observe(viewLifecycleOwner) { loginUi ->
            loginUi.apply(pb_reset_password)
        }
    }

    private fun isValidate(): Boolean =
         userViewModel.validateEmail(
            et_email,
            lt_email
        )

    private fun setupListeners() {
        et_email.addTextChangedListener(TextFieldValidation(et_email))
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.et_email -> {
                    userViewModel.validateEmail(et_email, lt_email)
                }
            }
        }
    }
}