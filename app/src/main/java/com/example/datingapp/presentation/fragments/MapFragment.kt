package com.example.datingapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.datingapp.R
import com.example.datingapp.presentation.MainActivity
import com.example.datingapp.presentation.viewModel.UserViewModel
import com.example.datingapp.common.ResourceAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.map_fragment.*

class MapFragment : Fragment(R.layout.map_fragment) {
    private val args: MapFragmentArgs by navArgs()
    private lateinit var auth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("back button", "Back button clicks")
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        userViewModel = (activity as MainActivity).userViewModel

        val user = args.user

        name.text = getString(R.string.name_txt_view, user.name)
        email.text = getString(R.string.email_txt_view, user.email)

        btn_sign_out.setOnClickListener {
            auth.signOut()
            userViewModel.setUser(null)
            userViewModel.setUserProfile(null)
            findNavController().navigate(R.id.action_mapFragment_to_loginFragment)
        }
    }
}
