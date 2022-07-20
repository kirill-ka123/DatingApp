package com.example.datingapp.presentation.viewModel

import android.view.View

interface ResetPasswordUi {
    fun apply(progressBar: View)

    class SuccessResetPasswordUi() : ResetPasswordUi {
        override fun apply(progressBar: View) {
            progressBar.visibility = View.INVISIBLE
        }
    }

    class LoadingResetPasswordUi() : ResetPasswordUi {
        override fun apply(progressBar: View) {
            progressBar.visibility = View.VISIBLE
        }
    }

    class ErrorResetPasswordUi() : ResetPasswordUi {
        override fun apply(progressBar: View) {
            progressBar.visibility = View.INVISIBLE
        }
    }
}