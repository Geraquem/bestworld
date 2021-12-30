package com.ftbw.app.bestworld.neworden.view.userprofile

import com.ftbw.app.bestworld.model.user.UserDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserProfileRepository(var listener: IUserProfile) {

    fun getUserData(userKey: String, isMainUserProfile: Boolean) {
        Firebase.database.reference.child("users").child(userKey).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    getUserSocialNetwork(
                        userKey,
                        it.getValue(UserDTO::class.java),
                        isMainUserProfile
                    )
                }
            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun getUserSocialNetwork(userKey: String, user: UserDTO?, isMainUserProfile: Boolean) {
        Firebase.database.reference.child("users").child(userKey).child("added").get()
            .addOnSuccessListener {
                if (user != null) {
                    user.addedCount = it.childrenCount
                    if (isMainUserProfile) {
                        listener.userData(user)
                    } else {
                        checkIfUserIsAlreadyAdded(userKey, user)
                    }
                }
            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun checkIfUserIsAlreadyAdded(userKey: String, user: UserDTO) {
        var isAdded = false
        val currentUserKey = Firebase.auth.currentUser!!.uid
        if (userKey != currentUserKey) {
            Firebase.database.reference.child("users").child(currentUserKey).child("added").get()
                .addOnSuccessListener {
                    for (key in it.children) {
                        if (userKey == key.key) {
                            isAdded = true
                        }
                    }
                    listener.isAddedToMyNetwork(isAdded)
                    listener.userData(user)

                }.addOnFailureListener {
                    listener.somethingWentWrong()
                }
        }
    }

    fun addUserToMyNetwork(userKey: String, add: Boolean) {
        val currentUserKey = Firebase.auth.currentUser!!.uid
        if (userKey != currentUserKey) {
            val reference = Firebase.database.reference.child("users")
                .child(currentUserKey).child("added").child(userKey)
            if (add) {
                reference.setValue(true).addOnCompleteListener {
                    when {
                        it.isSuccessful -> listener.isAddedToMyNetwork(true)
                        else -> listener.somethingWentWrong()
                    }
                }
            } else {
                reference.removeValue().addOnCompleteListener {
                    when {
                        it.isSuccessful -> listener.isAddedToMyNetwork(false)
                        else -> listener.somethingWentWrong()
                    }
                }
            }
        }
    }

    interface IUserProfile {
        fun userData(user: UserDTO)
        fun isAddedToMyNetwork(isAdded: Boolean)
        fun somethingWentWrong()
    }
}