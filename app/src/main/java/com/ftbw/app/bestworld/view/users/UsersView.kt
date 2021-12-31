package com.ftbw.app.bestworld.view.users

import com.ftbw.app.bestworld.model.user.UserRecyclerDTO

interface UsersView {
    fun showUsers(users: List<UserRecyclerDTO>)
    fun somethingWentWrong()
}