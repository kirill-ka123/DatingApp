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
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : Fragment(R.layout.register_fragment) {
    private lateinit var userViewModel: UserViewModel
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = (activity as MainActivity).userViewModel
        navController = findNavController()

        btn_register.setOnClickListener {
            val name = et_name.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            val rePassword = et_re_password.text.toString()

            if (Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches() && password.isNotBlank() && password == rePassword
            ) {
                userViewModel.signUp(email, password, name)
            } else if (password != rePassword) {
                Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Некорректный email", Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(requireContext(), "Некорректный пароль", Toast.LENGTH_SHORT).show()
            }
        }

        userViewModel.loginUi.observe(viewLifecycleOwner) { loginUi ->
            loginUi.apply(pb_sign_up, navController, requireContext(), this)
        }
    }

//    public class PasswordValidator {   private static final String PASSWORD_PATTERN =   "((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"; private final Pattern pattern; private Matcher matcher; public PasswordValidator()
//        { pattern = Pattern.compile(PASSWORD_PATTERN); }  public boolean validate(String password)
//        { matcher = pattern.matcher(password); return matcher.matches(); }
//    }
//
//    private fun isEmailCorrect(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
//
//    private fun isPasswordCorrect(password: String) {
//        if () {
//
//        }
//    }
}