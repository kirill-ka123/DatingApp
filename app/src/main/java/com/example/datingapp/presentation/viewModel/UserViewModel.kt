package com.example.datingapp.presentation.viewModel

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.datingapp.domain.models.UserProfile
import com.example.datingapp.domain.callbacks.GetUserCallback
import com.example.datingapp.domain.callbacks.SaveUserCallback
import com.example.datingapp.domain.callbacks.SignInCallback
import com.example.datingapp.domain.callbacks.SignUpCallback
import com.example.datingapp.domain.models.UserEmailAndPassword
import com.example.datingapp.domain.useCases.GetUserFromDbUseCase
import com.example.datingapp.domain.useCases.SignInUseCase
import com.example.datingapp.domain.useCases.SignUpUseCase
import com.example.datingapp.domain.useCases.SaveUserInDbUseCase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class UserViewModel(
    app: Application,
    private val getUserFromDbUseCase: GetUserFromDbUseCase,
    private val saveUserInDbUseCase: SaveUserInDbUseCase,
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
) : AndroidViewModel(app) {

    private var user: FirebaseUser? = null

    private val _loginUi = MutableLiveData<LoginUi>()
    val loginUi: LiveData<LoginUi> = _loginUi

    private val auth = FirebaseAuth.getInstance()

    init {
        setUser()
    }

    fun setUser() {
        if (user == null && auth.currentUser != null) {
            user = auth.currentUser

            getUserFromDbUseCase(user!!.uid, provideGetUserCallback())
        }
    }

    fun setUser(user: FirebaseUser?) {
        this.user = user
    }

    fun setUserProfile(userProfile: UserProfile?) {
        if (userProfile != null) {
            _loginUi.value = LoginUi.SuccessLoginUi(userProfile)
        } else _loginUi.value = LoginUi.NullLoginUi()
    }

    fun signIn(email: String, password: String) {

        val userEmailAndPassword = UserEmailAndPassword(email, password)
        signInUseCase(userEmailAndPassword, provideSignInCallback())
    }

    fun signUp(email: String, password: String, name: String) {

        val userEmailAndPassword = UserEmailAndPassword(email, password)
        signUpUseCase(userEmailAndPassword, provideSignUpCallback(email, name))
    }

    /**
     * field must not be empty
     */
    fun validateName(etName: TextInputEditText, ltName: TextInputLayout): Boolean {
        if (etName.text.toString().trim().isEmpty()) {
            ltName.error = "Поле не может быть пустым"
            etName.requestFocus()
            return false
        } else {
            ltName.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) field must not be empty
     * 2) text should matches email address format
     */
    fun validateEmail(etEmail: TextInputEditText, ltEmail: TextInputLayout): Boolean {
        if (etEmail.text.toString().trim().isEmpty()) {
            ltEmail.error = "Поле не может быть пустым"
            etEmail.requestFocus()
            return false
        } else if (!isValidEmail(etEmail.text.toString())) {
            ltEmail.error = "Некорректный email"
            etEmail.requestFocus()
            return false
        } else {
            ltEmail.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) field must not be empty
     * 2) password length must not be less than 6
     * 3) password must contain at least one digit
     * 4) password must contain at least one upper and one lower case letter
     * 5) password must contain at least one special character.
     */
    fun validatePassword(etPassword: TextInputEditText, ltPassword: TextInputLayout): Boolean {
        if (etPassword.text.toString().trim().isEmpty()) {
            ltPassword.error = "Поле не может быть пустым"
           etPassword.requestFocus()
            return false
        } else if (etPassword.text.toString().length < 6 || etPassword.text.toString().length > 30) {
            ltPassword.error = "Пароль должен содержать от 6 до 30 символов"
           etPassword.requestFocus()
            return false
        } else if (!isStringContainNumber(etPassword.text.toString())) {
            ltPassword.error = "Пароль должен содержать хотя бы одну цифру"
           etPassword.requestFocus()
            return false
        } else if (!isStringLowerAndUpperCase(etPassword.text.toString())) {
            ltPassword.error =
                "Пароль должен содержать прописные и строчные буквы"
           etPassword.requestFocus()
            return false
        } else {
            ltPassword.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) field must not be empty
     */
    fun validatePasswordLogin(etPassword: TextInputEditText, ltPassword: TextInputLayout): Boolean {
        if (etPassword.text.toString().trim().isEmpty()) {
            ltPassword.error = "Поле не может быть пустым"
            etPassword.requestFocus()
            return false
        } else {
            ltPassword.isErrorEnabled = false
        }
        return true
    }

    /**
     * 1) field must not be empty
     * 2) password and confirm password should be same
     */
    fun validateConfirmPassword(etConfirmPassword: TextInputEditText, ltConfirmPassword: TextInputLayout, etPassword: TextInputEditText): Boolean {
        when {
            etConfirmPassword.text.toString().trim().isEmpty() -> {
                ltConfirmPassword.error = "Поле не может быть пустым"
                etConfirmPassword.requestFocus()
                return false
            }
            etConfirmPassword.text.toString() != etPassword.text.toString() -> {
                ltConfirmPassword.error = "Пароли не совпадают"
                etConfirmPassword.requestFocus()
                return false
            }
            else -> {
                ltConfirmPassword.isErrorEnabled = false
            }
        }
        return true
    }

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isStringContainNumber(str: String) = str.contains(".*\\d.*".toRegex())

    private fun isStringLowerAndUpperCase(str: String) = str.contains("[a-z]".toRegex()) && str.contains("[A-Z]".toRegex())

    private fun provideSignInCallback() = object : SignInCallback {
        override fun onStartSignIn() {
            Log.i("firebase authentication", "signInWithEmail:start")

            _loginUi.value = LoginUi.LoadingLoginUi()
        }

        override fun onSuccessSignIn() {
            Log.d("firebase authentication", "signInWithEmail:success")

            if (auth.currentUser != null) {
                user = auth.currentUser!!

                // получение профиля пользователя из БД
                getUserFromDbUseCase(auth.currentUser!!.uid, provideGetUserCallback())
            } else {
                _loginUi.value = LoginUi.NullLoginUi()
            }
        }

        override fun onFailureSignIn(error: Exception?) {
            Log.w("firebase authentication", "signInWithEmail:failure", error)

            _loginUi.value = LoginUi.ErrorLoginUi(error?.message)
        }
    }

    private fun provideSignUpCallback(email: String, name: String) = object : SignUpCallback {
        override fun onStartSignUp() {
            Log.i("firebase authentication", "createUserWithEmail:start")

            _loginUi.value = LoginUi.LoadingLoginUi()
        }

        override fun onSuccessSignUp() {
            Log.d("firebase authentication", "createUserWithEmail:success")

            if (auth.currentUser != null) {
                user = auth.currentUser!!
                val userProfile = UserProfile(auth.currentUser!!.uid, name, email)
                // записываем профиль нового пользователя в БД
                saveUserInDbUseCase(userProfile, provideSaveUserCallback(userProfile))
            } else {
                _loginUi.value = LoginUi.NullLoginUi()
            }
        }

        override fun onFailureSignUp(error: Exception?) {
            Log.w("firebase authentication", "createUserWithEmail:failure", error)

            _loginUi.value = LoginUi.ErrorLoginUi(error?.message)
        }
    }

    private fun provideGetUserCallback() = object : GetUserCallback {
        override fun onStartGetUser() {
            Log.i("firebase database", "getUserFromDatabase:start")

            _loginUi.value = LoginUi.LoadingLoginUi()
        }

        override fun onSuccessGetUser(userProfile: UserProfile) {
            Log.i(
                "firebase database",
                "getUserFromDatabase:success, value:${userProfile}"
            )

            _loginUi.value = LoginUi.SuccessLoginUi(userProfile)
        }

        override fun onFailureGetUser(error: Exception) {
            Log.e("firebase database", "getUserFromDatabase:failure", error)

            _loginUi.value = LoginUi.ErrorLoginUi(error.message)
        }
    }

    private fun provideSaveUserCallback(userProfile: UserProfile) = object : SaveUserCallback {
        override fun onStartSaveUser() {
            Log.w("firebase database", "saveUserInDatabase:start")

            _loginUi.value = LoginUi.LoadingLoginUi()
        }

        override fun onSuccessSaveUser() {
            Log.w("firebase database", "saveUserInDatabase:success")

            _loginUi.value = LoginUi.SuccessLoginUi(userProfile)
        }

        override fun onFailureSaveUser(error: Exception?) {
            Log.w("firebase database", "saveUserInDatabase:failure", error)

            _loginUi.value = LoginUi.ErrorLoginUi(error?.message)
        }
    }
}

interface ValidateSignUp {

    fun apply(
        view: View
    )

    class ValidateName(): ValidateSignUp {
        override fun apply(
            view: View
        ) {

        }

    }
}