package com.ftbw.app.bestworld.view.userprofile

import com.ftbw.app.bestworld.model.user.UserDTO

interface UserProfileView {
    fun setUserData(user: UserDTO)

    fun showLinearButtons(view: Int)
    fun showAddButton(view: Int)
    fun modifyAddButton(isAdded: Boolean)

    fun somethingWentWrong()
}
