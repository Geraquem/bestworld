package com.ftbw.app.bestworld.view.userprofile

import android.view.View
import com.ftbw.app.bestworld.model.user.UserDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserProfilePresenter(var view: UserProfileView) : UserProfileRepository.IUserProfile {

    private val repository by lazy { UserProfileRepository(this) }

    fun checkIfIsMainUserProfile(userKey: String) {
        val currentUser = Firebase.auth.currentUser
        return if (currentUser == null || userKey == currentUser.uid) {
            view.showAddButton(View.GONE)
            view.showEditProfileButton(View.VISIBLE)
            repository.getUserData(userKey, true)
        } else {
            view.showAddButton(View.VISIBLE)
            view.showEditProfileButton(View.GONE)
            repository.getUserData(userKey, false)
        }
    }

    fun addUserToMyNetwork(userKey: String, shouldAdd: Boolean){
        repository.addUserToMyNetwork(userKey, shouldAdd)
    }

    override fun userData(user: UserDTO) {
        view.setUserData(user)
    }

    override fun isAddedToMyNetwork(isAdded: Boolean) {
        view.modifyAddButton(isAdded)
    }

    override fun somethingWentWrong() {
        view.somethingWentWrong()
    }
}