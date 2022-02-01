package com.ftbw.app.bestworld.view.userprofile.tabs.events.tabs

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserProfileEventsRVTabRepository(var listener: IUserProfileRVTab) {

    fun getAllEventsRelatedWithUser(relation: String, userKey: String) {
        val keyList = mutableListOf<String>()
        Firebase.database.reference.child("users").child(userKey)
            .child(relation).get().addOnSuccessListener {
                for (label in it.children) {
                    for (key in label.children) {
                        keyList.add(key.key.toString())
                    }
                }
                getAllEvents(keyList)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun getAllEvents(keyList: MutableList<String>) {
        val events: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").get().addOnSuccessListener {
            for (label in it.children) {
                for (event in label.children) {
                    if (keyList.contains(event.key.toString())) {
                        events.add(event.getValue(EventRecyclerDTO::class.java)!!)
                    }
                }
            }
            listener.allEvents(events)

        }.addOnFailureListener {
            listener.somethingWentWrong()
        }
    }

    fun getEventsRelatedWithUser(relation: String, userKey: String, eventLabel: String) {
        val keyList: MutableList<String> = mutableListOf()
        Firebase.database.reference.child("users").child(userKey)
            .child(relation).child(eventLabel).get()
            .addOnSuccessListener {
                keyList.clear()
                for (event in it.children) {
                    keyList.add(event.key.toString())
                }
                givenListOfKeysGetEvents(keyList, eventLabel)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    private fun givenListOfKeysGetEvents(keyList: List<String>, eventLabel: String) {
        val events: MutableList<EventRecyclerDTO> = mutableListOf()
        Firebase.database.reference.child("events").child(eventLabel)
            .get().addOnSuccessListener {
                events.clear()
                for (event in it.children) {
                    for (key in keyList) {
                        if (event.key == key) {
                            events.add(event.getValue(EventRecyclerDTO::class.java)!!)
                        }
                    }
                }
                listener.events(events)

            }.addOnFailureListener {
                listener.somethingWentWrong()
            }
    }

    interface IUserProfileRVTab {
        fun allEvents(events: List<EventRecyclerDTO>)
        fun events(events: List<EventRecyclerDTO>)
        fun somethingWentWrong()
    }
}