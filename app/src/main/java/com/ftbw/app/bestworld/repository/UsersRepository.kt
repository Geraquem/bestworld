package com.ftbw.app.bestworld.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.RegisteredUserDTO
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsersRepository constructor(val application: Application) {

    val user = MutableLiveData<UserDTO>()
    val isUserSaved = MutableLiveData<Boolean>()
    val isUserAdded = MutableLiveData<Boolean>()
    val isUserAlreadyAdded = MutableLiveData<Boolean>()
    val listUsers = MutableLiveData<List<UserRecyclerDTO>>()

    fun saveUser(name: String, email: String, key: String, type: String) {
        val user = RegisteredUserDTO(name, email, key, type)
        Firebase.database.reference.child("users").child(key).setValue(user)
            .addOnCompleteListener {
                saveUserByType(type, key)
            }
    }

    private fun saveUserByType(type: String, key: String) {
        Firebase.database.reference.child("usersByTypes").child(type).child(key).setValue(true)
            .addOnCompleteListener {
                isUserSaved.value = it.isSuccessful
            }
    }

    fun getUser(key: String) {
        Firebase.database.reference.child("users").child(key).get()
            .addOnSuccessListener {
                getAddedByUser(key, it.getValue(UserDTO::class.java))

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

    fun addUserToMyNetwork(userKey: String, add: Boolean) {
        val currentUserKey = Firebase.auth.currentUser!!.uid
        if (userKey != currentUserKey) {
            val reference = Firebase.database.reference.child("users").child(currentUserKey)
                .child("added").child(userKey)
            if (add) {
                reference.setValue(true).addOnCompleteListener {
                    if (it.isSuccessful) {
                        isUserAdded.value = true
                    }
                }
            } else {
                reference.removeValue().addOnCompleteListener {
                    if (it.isSuccessful) {
                        isUserAdded.value = false
                    }
                }
            }
        }
    }

    fun checkIfUserIsAlreadyAdded(userKey: String) {
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
                    isUserAlreadyAdded.value = isAdded

                }.addOnFailureListener {
                    System.out.println("------- NOPE, DATABASE ERROR")
                }
        }
    }
}
