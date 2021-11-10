package com.ftbw.app.bestworld.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.EventDTO
import com.ftbw.app.bestworld.model.EventRecyclerDTO
import com.ftbw.app.bestworld.model.UserDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UsersRepository constructor(val application: Application) {

    val user = MutableLiveData<UserDTO>()

    fun getUser(type: String, key: String) {
        Firebase.database.reference.child("users").child(type).child(key).get()
            .addOnSuccessListener {
                user.value = it.getValue(UserDTO::class.java)

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

}