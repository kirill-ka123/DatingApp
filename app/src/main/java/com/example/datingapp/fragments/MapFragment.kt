package com.example.datingapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.datingapp.R
import com.example.datingapp.ui.AuthViewModel
import com.example.datingapp.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.map_fragment.*

class MapFragment : Fragment(R.layout.map_fragment) {
    val args: MapFragmentArgs by navArgs()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("BACKBUTTON", "Back button clicks")
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        authViewModel = (activity as MainActivity).authViewModel

        val user = args.user

        name.text = getString(R.string.name_txt_view, user.displayName)
        email.text = getString(R.string.email_txt_view, user.email)

        btn_sign_out.setOnClickListener {
            mAuth.signOut()
            authViewModel.setUser(null)
            findNavController().navigate(R.id.action_mapFragment_to_loginFragment)
        }
    }
}
