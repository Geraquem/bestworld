package com.ftbw.app.bestworld.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EventsRepository constructor(val application: Application) {

    val listEventRecycler = MutableLiveData<List<EventRecyclerDTO>>()

    fun getEnvironmentalEvents() {
        val auxList: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child("environmental").get()
            .addOnSuccessListener {
                auxList.clear()
                for (event in it.children) {
                    auxList.add(event.getValue(EventRecyclerDTO::class.java)!!)
                }
                listEventRecycler.value = auxList

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }
}