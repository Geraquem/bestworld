package com.ftbw.app.bestworld.neworden.repository.old

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftbw.app.bestworld.model.event.EventDTO
import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EventsRepository constructor(val application: Application) {

    val listEventRecycler = MutableLiveData<List<EventRecyclerDTO>>()
    val listOfEvents = MutableLiveData<List<EventRecyclerDTO>>()
    val event = MutableLiveData<EventDTO>()
    val isEventSaved = MutableLiveData<Boolean>()
    val isUserAlreadySignedUp = MutableLiveData<Boolean>()
    val isUserGoingToAssist = MutableLiveData<Boolean>()
    val numberOfAssistants = MutableLiveData<Long>()

    fun getAllEvents() {
        val labelList: MutableList<String> = mutableListOf()
        Firebase.database.reference.child("events")
            .get().addOnSuccessListener {
                for (label in it.children) {
                    labelList.add(label.key.toString())
                }
                getAllEventsGivenLabels(labelList)

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    private fun getAllEventsGivenLabels(labels: MutableList<String>) {
        val auxList: MutableList<EventRecyclerDTO> = mutableListOf()
        for (eventLabel in labels) {
            Firebase.database.reference.child("events").child(eventLabel)
                .get().addOnSuccessListener {
                    for (event in it.children) {
                        auxList.add(event.getValue(EventRecyclerDTO::class.java)!!)
                    }
                    listEventRecycler.value = auxList
                }
        }
    }

    fun getEventsByLabel(eventLabel: String) {
        val auxList: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(eventLabel)
            .get().addOnSuccessListener {
                auxList.clear()
                for (event in it.children) {
                    auxList.add(event.getValue(EventRecyclerDTO::class.java)!!)
                }
                listEventRecycler.value = auxList

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    fun getEventsRelatedWithUser(relation: String, userKey: String, eventLabel: String) {
        val keyList: MutableList<String> = mutableListOf()
        Firebase.database.reference.child("users").child(userKey)
            .child(relation).child(eventLabel)
            .get().addOnSuccessListener {
                keyList.clear()
                for (event in it.children) {
                    keyList.add(event.key.toString())
                }
                givenListOfKeysGetEvents(keyList, eventLabel)

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    private fun givenListOfKeysGetEvents(keyList: List<String>, eventLabel: String) {
        val auxList: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(eventLabel)
            .get().addOnSuccessListener {
                auxList.clear()
                for (event in it.children) {
                    for (key in keyList) {
                        if (event.key == key) {
                            auxList.add(event.getValue(EventRecyclerDTO::class.java)!!)
                        }
                    }
                }
                listOfEvents.value = auxList

            }.addOnFailureListener {
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }
}