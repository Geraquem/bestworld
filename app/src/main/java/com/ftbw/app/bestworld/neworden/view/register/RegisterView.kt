package com.ftbw.app.bestworld.neworden.view.register

interface RegisterView {
    fun doRegister(name: String, email: String, password: String)
    fun setErrorMessage(message: Int)
    fun userSavedOk()
}