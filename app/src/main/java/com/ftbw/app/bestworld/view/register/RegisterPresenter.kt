package com.ftbw.app.bestworld.view.register

import android.net.Uri
import com.ftbw.app.bestworld.R
import com.ftbw.app.bestworld.helper.EventCommon.Companion.COMPANY
import com.ftbw.app.bestworld.helper.EventCommon.Companion.PARTICULAR
import com.ftbw.app.bestworld.model.user.UserDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterPresenter(val view: RegisterView) : RegisterRepository.IUser, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

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
            true -> COMPANY
            false -> PARTICULAR
        }
    }

    fun saveUser(user: UserDTO, imageUri: Uri?) {
        launch(Dispatchers.IO) {
            repository.saveUser(user, imageUri)
        }
    }

    override fun userSavedOk() {
        launch {
            view.userSavedOk()
        }
    }

    override fun somethingWentWrong() {
        launch {
            view.setErrorMessage(R.string.somethingWentWrong)
        }
    }
}