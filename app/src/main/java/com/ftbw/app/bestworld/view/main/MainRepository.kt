package com.ftbw.app.bestworld.view.main

import com.ftbw.app.bestworld.model.user.UserDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainRepository(private val listener: IUserData) {

    fun getUserData(userKey: String) {
        Firebase.database.reference.child("users").child(userKey).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    it.getValue(UserDTO::class.java)?.let { user -> listener.userData(user) }
                }
            }.addOnFailureListener {
                listener.cantFindUser()
            }
    }

    interface IUserData {
        fun userData(user: UserDTO)
        fun cantFindUser()
    }
}
