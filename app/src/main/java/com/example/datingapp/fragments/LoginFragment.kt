package com.example.datingapp.fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.datingapp.R
import com.example.datingapp.models.User
import com.example.datingapp.ui.AuthViewModel
import com.example.datingapp.ui.MainActivity
import com.example.datingapp.util.Constant.Companion.TAG_LOG
import com.example.datingapp.util.ResourceAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        authViewModel = (activity as MainActivity).authViewModel

        btn_sign_in.setOnClickListener {
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.isNotBlank()) {
                authViewModel.signIn(email, password)
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Некорректный email", Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(requireContext(), "Некорректный пароль", Toast.LENGTH_SHORT).show()
            }
        }

        btn_sign_up.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        authViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            when(user) {
                is ResourceAuth.Success -> {
                    val bundle = Bundle().apply {
                        if (user.data != null) {
                            putSerializable("user", User(user.data))
                        } else {
                            putSerializable("user", User("null", "null"))
                        }
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_mapFragment, bundle)
                }
                is ResourceAuth.Error -> {
                    Log.d(TAG_LOG, user.message.toString())
                    Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                }
                is ResourceAuth.Loading -> {

                }
                is ResourceAuth.Null -> {

                }
            }
        })
    }

    override fun onStart() {
        super.onStart()

        authViewModel.setUser(mAuth.currentUser)
    }

//    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
//        val response = result.idpResponse
//        if (result.resultCode == RESULT_OK) {
//            // Successfully signed in
//            val user = FirebaseAuth.getInstance().currentUser
//            Toast.makeText(
//                requireContext(), "Успешная аутентификация.",
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            // Sign in failed. If response is null the user canceled the
//            // sign-in flow using the back button. Otherwise check
//            // response.getError().getErrorCode() and handle the error.
//            Toast.makeText(
//                requireContext(), "Ошибка аутентификации.",
//                Toast.LENGTH_SHORT
//            ).show()
//            response?.error?.errorCode
//        }
//    }
//
//    private fun signUp(email: String, password: String) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(
//                requireActivity()
//            ) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG_LOG, "createUserWithEmail:success")
//                    Toast.makeText(
//                        requireContext(), "Успешная аутентификация.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    val user = mAuth.currentUser
//                    //updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG_LOG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        requireContext(), "Ошибка аутентификации.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    //updateUI(null)
//                }
//            }
//    }
//
//    private fun signIn(email: String, password: String) {
//        mAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(
//                requireActivity()
//            ) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG_LOG, "signInWithEmail:success")
//                    val user = mAuth.currentUser
//                    //updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG_LOG, "signInWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        requireContext(), "Authentication failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    //updateUI(null)
//                }
//            }
//    }
}