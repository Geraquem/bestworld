package com.ftbw.app.bestworld.neworden.view.login

class LoginPresenter(val view: LoginView) {

    fun checkCredentials(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            view.doLogin(email, password)
        }
    }

    fun updateUI() {
        view.updateUI()
    }

    fun loginUnSuccessful() {
        view.loginUnSuccessful()
    }
}