package com.ftbw.app.bestworld.view.userprofile.tabs

import com.ftbw.app.bestworld.model.event.EventRecyclerDTO
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserProfileRVTabRepository(var listener: IUserProfileRVTab) {

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
                System.out.println("------- NOPE, DATABASE ERROR")
            }
    }

    interface IUserProfileRVTab {
        fun events(events: List<EventRecyclerDTO>)
        fun somethingWentWrong()
    }
}