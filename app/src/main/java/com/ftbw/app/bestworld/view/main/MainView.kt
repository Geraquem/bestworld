package com.ftbw.app.bestworld.view.main

import com.ftbw.app.bestworld.model.user.UserDTO

interface MainView {
    fun showUserData(user: UserDTO)
    fun cantFindUser()
}