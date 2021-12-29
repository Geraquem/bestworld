package com.ftbw.app.bestworld.neworden.view.register

import android.net.Uri
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.model.user.UserDTO

class RegisterPresenter(val view: RegisterView) : RegisterRepository.IUser {

    private val repository by lazy { RegisterRepository(this) }

    fun checkCredentials(name: String, email: String, password: String, repeatedPassword: String) {
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatedPassword.isNotEmpty()) {
            if (!isThereFailures(email, password, repeatedPassword)) {
                view.doRegister(name, email, password)
            }
        }
    }

    private fun isThereFailures(
        email: String,
        password: String,
        repeatedPassword: String
    ): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.setErrorMessage(R.string.errorMessageEmailNotValid)
            return true
        }
        if (password.length < 6) {
            view.setErrorMessage(R.string.errorMessagePasswords6chars)
            return true
        }
        if (password != repeatedPassword) {
            view.setErrorMessage(R.string.errorMessageNotMatchingPasswords)
            return true
        }
        return false
    }

    fun getRadioButton(isCompany: Boolean): String {
        return when (isCompany) {
            true -> "company"
            false -> "particular"
        }
    }

    fun saveUser(user: UserDTO, imageUri: Uri?){
        repository.saveUser(user, imageUri)
    }

    override fun userSavedOk() {
        view.userSavedOk()
    }

    override fun somethingWentWrong() {
        view.setErrorMessage(R.string.somethingWentWrong)
    }
}