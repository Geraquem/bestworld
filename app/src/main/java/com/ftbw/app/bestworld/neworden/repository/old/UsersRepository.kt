package com.ftbw.app.bestworld.neworden.repository.old

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UsersRepository(val application: Application) {

    val user = MutableLiveData<UserDTO>()
    val creator = MutableLiveData<UserRecyclerDTO>()
    val isUserSaved = MutableLiveData<Boolean>()
    val isUserAdded = MutableLiveData<Boolean>()
    val isUserAlreadyAdded = MutableLiveData<Boolean>()
    val listUsers = MutableLiveData<List<UserRecyclerDTO>>()

    fun getCreatorOfEvent(key: String) {
        Firebase.database.reference.child("users").child(key).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    creator.value = it.getValue(UserRecyclerDTO::class.java)
                }
            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun getUser(key: String) {
        Firebase.database.reference.child("users").child(key).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    getAddedByUser(key, it.getValue(UserDTO::class.java))
                }

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    private fun getAddedByUser(key: String, user: UserDTO?) {
        Firebase.database.reference.child("users").child(key).child("added").get()
            .addOnSuccessListener {
                if (user != null) {
                    user.addedCount = it.childrenCount
                    this.user.value = user
                }

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

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
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    private fun givenListOfKeysGetUsers(keyList: List<String>) {
        val auxList: MutableList<UserRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("users").get()
            .addOnSuccessListener {
                auxList.clear()
                for (user in it.children) {
                    for (key in keyList) {
                        if (user.key == key) {
                            auxList.add(user.getValue(UserRecyclerDTO::class.java)!!)
                        }
                    }
                }
                listUsers.value = auxList

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }


}
