package com.ftbw.app.bestworld.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.RegisteredUserDTO
import com.ftbw.app.bestworld.model.UserDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsersRepository constructor(val application: Application) {

    val user = MutableLiveData<UserDTO>()
    val isUserSaved = MutableLiveData<Boolean>()

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
                run {
                    isUserSaved.value = true
                }
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

}