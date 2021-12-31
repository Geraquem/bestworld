package com.ftbw.app.bestworld.neworden.view.users

import com.ftbw.app.bestworld.model.user.UserRecyclerDTO

class UsersPresenter(private val view: UsersView) : UsersRepository.IUsers {

    private val repository by lazy { UsersRepository(this) }

    fun getUsersByType(type: String) {
        repository.getUsersByType(type)
    }

    override fun showUsers(users: List<UserRecyclerDTO>) {
        view.showUsers(users)
    }

    override fun somethingWentWrong() {
        view.somethingWentWrong()
    }
}