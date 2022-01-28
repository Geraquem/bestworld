package com.ftbw.app.bestworld.view.userprofile

import android.view.View
import com.ftbw.app.bestworld.model.user.UserDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserProfilePresenter(var view: UserProfileView) : UserProfileRepository.IUserProfile,
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private val repository by lazy { UserProfileRepository(this) }

    fun checkIfIsMainUserProfile(userKey: String) {
        val currentUser = Firebase.auth.currentUser

        return if (currentUser == null) {
            view.showAddButton(View.GONE)
            view.showLinearButtons(View.GONE)
            getUserData(userKey, true)

        } else if (userKey == currentUser.uid) {
            view.showAddButton(View.GONE)
            view.showLinearButtons(View.VISIBLE)
            getUserData(userKey, true)

        } else {
            view.showAddButton(View.VISIBLE)
            view.showLinearButtons(View.GONE)
            getUserData(userKey, false)
        }
    }

    private fun getUserData(userKey: String, isMainUserProfile: Boolean) {
        launch(Dispatchers.IO) {
            repository.getUserData(userKey, isMainUserProfile)
        }
    }

    fun addUserToMyNetwork(userKey: String, shouldAdd: Boolean) {
        launch(Dispatchers.IO) {
            repository.addUserToMyNetwork(userKey, shouldAdd)
        }
    }

    override fun userData(user: UserDTO) {
        launch {
            view.setUserData(user)
        }
    }

    override fun isAddedToMyNetwork(isAdded: Boolean) {
        launch {
            view.modifyAddButton(isAdded)
        }
    }

    override fun somethingWentWrong() {
        launch {
            view.somethingWentWrong()
        }
    }
}