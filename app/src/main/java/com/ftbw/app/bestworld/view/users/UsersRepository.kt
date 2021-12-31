package com.ftbw.app.bestworld.view.users

import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsersRepository(private val listener: IUsers) {

    fun getUsersByType(type: String) {
        val keyList: MutableList<String> = mutableListOf()
        Firebase.database.reference.child("usersByTypes").child(type)
            .get()
            .addOnSuccessListener {
                keyList.clear()
                for (user in it.children) {
                    keyList.add(user.key.toString())
                }
                givenListOfKeysGetUsers(keyList)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun givenListOfKeysGetUsers(keyList: List<String>) {
        val users: MutableList<UserRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("users").get()
            .addOnSuccessListener {
                users.clear()
                for (user in it.children) {
                    for (key in keyList) {
                        if (user.key == key) {
                            users.add(user.getValue(UserRecyclerDTO::class.java)!!)
                        }
                    }
                }
                listener.showUsers(users)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    interface IUsers {
        fun showUsers(users: List<UserRecyclerDTO>)
        fun somethingWentWrong()
    }
}