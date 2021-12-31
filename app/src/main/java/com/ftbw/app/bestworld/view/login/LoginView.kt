package com.ftbw.app.bestworld.view.login

interface LoginView {
    fun doLogin(email: String, password: String)
    fun updateUI()
    fun loginUnSuccessful()
}