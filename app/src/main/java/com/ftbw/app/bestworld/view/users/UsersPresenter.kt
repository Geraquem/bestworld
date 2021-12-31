package com.ftbw.app.bestworld.view.users

import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UsersPresenter(private val view: UsersView) : UsersRepository.IUsers, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { UsersRepository(this) }

    fun getUsersByType(type: String) {
        launch(Dispatchers.IO) {
            repository.getUsersByType(type)
        }
    }

    override fun showUsers(users: List<UserRecyclerDTO>) {
        launch {
            view.showUsers(users)
        }
    }

    override fun somethingWentWrong() {
        launch {
            view.somethingWentWrong()
        }
    }
}