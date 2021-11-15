package com.ftbw.app.bestworld.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EventsRepository constructor(val application: Application) {

    val isLoading = MutableLiveData<Boolean>()

    val listEventRecycler = MutableLiveData<List<EventRecyclerDTO>>()
    val event = MutableLiveData<EventDTO>()
    val isEventSaved = MutableLiveData<Boolean>()

    fun getEvents(eventLabel: String) {
        val auxList: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(eventLabel).get()
            .addOnSuccessListener {
                auxList.clear()
                for (event in it.children) {
                    auxList.add(event.getValue(EventRecyclerDTO::class.java)!!)
                }
                listEventRecycler.value = auxList
                isLoading.value = false

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun getSpecificEvent(eventLabel: String, key: String) {
        Firebase.database.reference.child("events").child(eventLabel).child(key).get()
            .addOnSuccessListener {
                event.value = it.getValue(EventDTO::class.java)

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun saveEvent(event: EventDTO) {
        val database = Firebase.database.reference

        val label = event.label
        val key = database.child(label!!).push().key
        event.key = key

        database.child("events").child(label).child(key!!).setValue(event).addOnCompleteListener {
            isEventSaved.value = it.isSuccessful
        }
    }
}