package com.ftbw.app.bestworld.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.RegisteredUserDTO
import com.ftbw.app.bestworld.model.user.UserDTO
import com.ftbw.app.bestworld.model.user.UserRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsersRepository constructor(val application: Application) {

    val user = MutableLiveData<UserDTO>()
    val isUserSaved = MutableLiveData<Boolean>()
    val listUsers = MutableLiveData<List<UserRecyclerDTO>>()

    fun saveUser(name: String, email: String, key: String, type: String) {
        val user = RegisteredUserDTO(name, email, key, type)
        Firebase.database.reference.child("users").child(key).setValue(user)
            .addOnCompleteListener {
                run {
                    saveUserByType(type, key)
                }
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
                user.value = it.getValue(UserDTO::class.java)

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